package com.tecno_comfenalco.pa.features.store.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.StoresDistributorsEntity;
import com.tecno_comfenalco.pa.features.store.dto.StoreDto;
import com.tecno_comfenalco.pa.features.store.dto.request.ClaimStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.EditStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreByDistributorRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ClaimStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.DisableStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.EditStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ListStoresResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.RegisterStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.StoresResponseDto;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;
import com.tecno_comfenalco.pa.features.store.repository.IStoresDistributorsRepository;
import com.tecno_comfenalco.pa.security.AuthenticationService;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.enums.StoreClaimStatus;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private IStoreRepository storeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IStoresDistributorsRepository storesDistributorsRepository;

    @Autowired
    private IDistributorRepository distributorRepository;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * FLUJO 1: Autogestión - El dueño registra su propia tienda
     * Crea Usuario + Tienda con estado SELF_REGISTERED
     */
    @Transactional
    public Result<RegisterStoreResponseDto, Exception> newStore(RegisterStoreRequestDto dtoStore) {
        boolean existsStore = storeRepository.existsByNIT(dtoStore.NIT());

        if (existsStore) {
            return Result.error(new Exception(
                    "La tienda con este NIT ya está registrada. Si eres el dueño, usa la función 'Reclamar Tienda'."));
        }

        try {
            // Crear usuario primero
            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(
                            dtoStore.name().toLowerCase().replace(" ", "_"),
                            "password",
                            Set.of("STORE"),
                            true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("User not found!"));

            // Crear tienda con usuario asignado y estado SELF_REGISTERED
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setNIT(dtoStore.NIT());
            storeEntity.setName(dtoStore.name());
            storeEntity.setPhoneNumber(dtoStore.phoneNumber());
            storeEntity.setEmail(dtoStore.email());
            storeEntity.setDirection(dtoStore.direction());
            storeEntity.setUser(userEntity);
            storeEntity.setClaimStatus(StoreClaimStatus.SELF_REGISTERED);

            storeRepository.save(storeEntity);

            RegisterStoreResponseDto response = new RegisterStoreResponseDto(
                    "Store registered successfully! You can now log in with your credentials.");

            return Result.ok(response);

        } catch (Exception e) {
            logger.error("Error registering store: {}", e.getMessage(), e);
            return Result.error(new Exception("Failed to register store: " + e.getMessage()));
        }
    }

    /**
     * FLUJO 2: Registro por Distribuidora - La distribuidora registra una tienda
     * sin usuario
     * Crea Tienda con estado PENDING_CLAIM + Relación StoresDistributors
     */
    @Transactional
    public Result<RegisterStoreResponseDto, Exception> registerStoreByDistributor(
            Long distributorId,
            RegisterStoreByDistributorRequestDto dtoStore) {

        try {
            // Verificar que la distribuidora existe
            DistributorEntity distributor = distributorRepository.findById(distributorId)
                    .orElseThrow(() -> new Exception("Distributor not found!"));

            // Verificar si la tienda ya existe
            Optional<StoreEntity> existingStore = storeRepository.findByNIT(dtoStore.NIT());

            StoreEntity storeEntity;

            if (existingStore.isPresent()) {
                storeEntity = existingStore.get();
                logger.info("Store with NIT {} already exists. Creating distributor relationship.", dtoStore.NIT());
            } else {
                // Crear nueva tienda SIN usuario (será reclamada después)
                storeEntity = new StoreEntity();
                storeEntity.setNIT(dtoStore.NIT());
                storeEntity.setName(dtoStore.name());
                storeEntity.setPhoneNumber(dtoStore.phoneNumber());
                storeEntity.setEmail(dtoStore.email());
                storeEntity.setDirection(dtoStore.direction());
                storeEntity.setUser(null); // Sin usuario
                storeEntity.setClaimStatus(StoreClaimStatus.PENDING_CLAIM);

                storeEntity = storeRepository.save(storeEntity);
                logger.info("New store created with NIT {} in PENDING_CLAIM status.", dtoStore.NIT());
            }

            // Crear relación Tienda-Distribuidora con código interno
            boolean relationshipExists = storesDistributorsRepository
                    .existsByStore_IdAndDistributor_Id(storeEntity.getId(), distributorId);

            if (!relationshipExists) {
                StoresDistributorsEntity relationship = new StoresDistributorsEntity();
                relationship.setStore(storeEntity);
                relationship.setDistributor(distributor);
                relationship.setInternalClientCode(dtoStore.internalClientCode());

                storesDistributorsRepository.save(relationship);
                logger.info("Relationship created between store {} and distributor {} with code: {}",
                        storeEntity.getId(), distributorId, dtoStore.internalClientCode());
            }

            RegisterStoreResponseDto response = new RegisterStoreResponseDto(
                    "Store registered successfully by distributor. The store owner can claim ownership later.");

            return Result.ok(response);

        } catch (Exception e) {
            logger.error("Error registering store by distributor: {}", e.getMessage(), e);
            return Result.error(new Exception("Failed to register store: " + e.getMessage()));
        }
    }

    /**
     * PROCESO DE CLAIM: El dueño reclama una tienda que fue registrada por una
     * distribuidora
     * Cambia estado de PENDING_CLAIM a CLAIMED y asocia el usuario
     */
    @Transactional
    public Result<ClaimStoreResponseDto, Exception> claimStore(ClaimStoreRequestDto dtoClaimRequest) {
        try {
            // 1. Buscar la tienda por NIT
            StoreEntity storeEntity = storeRepository.findByNIT(dtoClaimRequest.NIT())
                    .orElseThrow(() -> new Exception("No existe una tienda registrada con ese NIT."));

            // 2. Validar que la tienda esté en estado PENDING_CLAIM
            if (storeEntity.getClaimStatus() != StoreClaimStatus.PENDING_CLAIM) {
                return Result.error(new Exception(
                        "Esta tienda ya fue reclamada o registrada directamente. Estado actual: "
                                + storeEntity.getClaimStatus()));
            }

            // 3. Validar que el email coincida (medida de seguridad)
            if (!storeEntity.getEmail().equalsIgnoreCase(dtoClaimRequest.email())) {
                return Result.error(new Exception(
                        "El email no coincide con el registrado para esta tienda. Contacta al soporte."));
            }

            // 4. Crear el usuario propietario
            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(
                            storeEntity.getName().toLowerCase().replace(" ", "_"),
                            dtoClaimRequest.password(),
                            Set.of("STORE"),
                            true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("Error creating user!"));

            // 5. Asociar usuario a la tienda y cambiar estado
            storeEntity.setUser(userEntity);
            storeEntity.setClaimStatus(StoreClaimStatus.CLAIMED);
            storeRepository.save(storeEntity);

            logger.info("Store with NIT {} successfully claimed by user {}",
                    storeEntity.getNIT(), userEntity.getUsername());

            ClaimStoreResponseDto response = new ClaimStoreResponseDto(
                    "¡Tienda reclamada exitosamente! Ahora puedes ver todos los pedidos asociados a tu negocio.",
                    storeEntity.getId(),
                    userEntity.getId(),
                    userEntity.getUsername());

            return Result.ok(response);

        } catch (Exception e) {
            logger.error("Error claiming store: {}", e.getMessage(), e);
            return Result.error(new Exception("Failed to claim store: " + e.getMessage()));
        }
    }

    public Result<EditStoreResponseDto, Exception> editStore(Long id, EditStoreRequestDto dtoStore) {
        try {
            return storeRepository.findById(id)
                    .map(store -> {

                        store.setNIT(dtoStore.NIT());
                        store.setName(dtoStore.name());
                        store.setEmail(dtoStore.email());
                        store.setDirection(dtoStore.direction());

                        storeRepository.save(store);
                        EditStoreResponseDto response = new EditStoreResponseDto("Store editied succesfull!");
                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("store not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("store not exists by id cause!"));
        }
    }

    public Result<DisableStoreResponseDto, Exception> disableStore(Long id) {
        try {
            return storeRepository.findById(id).map(store -> {
                storeRepository.deleteById(id);
                DisableStoreResponseDto response = new DisableStoreResponseDto("store remove successfull!");
                return Result.ok(response);
            }).orElseGet(() -> Result.error(new Exception("store not found!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error deleting store!"));
        }
    }

    public Result<ListStoresResponseDto, Exception> listAllStores() {
        List<StoreEntity> storeEntities = storeRepository.findAll();

        try {
            List<StoreDto> storeDtos = storeEntities.stream()
                    .map(store -> new StoreDto(store.getNIT(), store.getName(), store.getPhoneNumber(),
                            store.getEmail(), store.getDirection()))
                    .toList();
            ListStoresResponseDto response = new ListStoresResponseDto(storeDtos, "stores found succesfull!");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to get all stores!"));
        }
    }

    public Result<StoresResponseDto, Exception> showStore(Long id) {
        try {
            return storeRepository.findById(id)
                    .map(store -> {
                        StoreDto storeDto = new StoreDto(store.getNIT(), store.getName(), store.getPhoneNumber(),
                                store.getEmail(), store.getDirection());
                        StoresResponseDto response = new StoresResponseDto(storeDto, "store show succesfull!");
                        return Result.ok(response);
                    })
                    .orElseGet(() -> Result.error(new Exception("Error found store!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error to show store!"));
        }
    }

}
