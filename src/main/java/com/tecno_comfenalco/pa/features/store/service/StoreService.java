package com.tecno_comfenalco.pa.features.store.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.dto.StoreDto;
import com.tecno_comfenalco.pa.features.store.dto.request.EditStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.response.DisableStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.EditStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ListStoresResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.RegisterStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.StoresResponseDto;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;
import com.tecno_comfenalco.pa.security.AuthenticationService;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    @Autowired
    private IStoreRepository storeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public Result<RegisterStoreResponseDto, Exception> newStore(RegisterStoreRequestDto dtoStore) {
        boolean existsStore = storeRepository.existsByNIT(dtoStore.NIT());

        if (existsStore) {
            return Result.error(new Exception("the store already registed!"));
        }

        try {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setNIT(dtoStore.NIT());
            storeEntity.setName(dtoStore.name());
            storeEntity.setPhoneNumber(dtoStore.phoneNumber());
            storeEntity.setEmail(dtoStore.email());
            storeEntity.setDirection(dtoStore.direction());

            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoStore.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("STORE"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));
            storeEntity.setUser(userEntity);

            storeRepository.save(storeEntity);

            RegisterStoreResponseDto response = new RegisterStoreResponseDto("Store register succesfull!");

            return Result.ok(response);

        } catch (Exception e) {
            logger.error("Error registering store: {}", e.getMessage(), e);
            return Result.error(new Exception("Failed to register store: " + e.getMessage()));
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
