package com.tecno_comfenalco.pa.features.presales.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.distributor.service.DistributorService;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.dto.PresalesDto;
import com.tecno_comfenalco.pa.features.presales.dto.request.EditPresalesRequestDto;
import com.tecno_comfenalco.pa.features.presales.dto.request.RegisterPresalesRequestDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.EditPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.ListPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.PresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.RegisterPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;
import com.tecno_comfenalco.pa.security.AuthenticationService;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class PresalesService {
    @Autowired
    private IDistributorRepository distributorRepository;

    @Autowired
    private IPresalesRepository presalesRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserRepository userRepository;

    public Result<RegisterPresalesResponseDto, Exception> newPresales(RegisterPresalesRequestDto dtoPresales) {

        boolean existsPresale = presalesRepository.existsByDocumentNumber(dtoPresales.documentNumber());

        if (existsPresale) {
            return Result.error(new Exception("The current presales is already registered!"));
        }

        try {

            PresalesEntity presalesEntity = new PresalesEntity();
            presalesEntity.setName(dtoPresales.name());
            presalesEntity.setPhoneNumber(dtoPresales.phoneNumber());
            presalesEntity.setEmail(dtoPresales.email());
            presalesEntity.setDocumentType(dtoPresales.documentType());
            presalesEntity.setDocumentNumber(dtoPresales.documentNumber());

            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoPresales.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("PRESALES"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));

            presalesEntity.setUser(userEntity);

            assignPresalesToDistributor(presalesEntity);

            presalesRepository.save(presalesEntity);

            return Result.ok(new RegisterPresalesResponseDto("Presales register successful!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error to register Presales!"));
        }
    }

    public Result<EditPresalesResponseDto, Exception> editPresales(Long id,
            EditPresalesRequestDto dtoPresales) {
        try {
            return presalesRepository.findById(id)
                    .map(presale -> {

                        presale.setName(dtoPresales.name());
                        presale.setPhoneNumber(dtoPresales.phoneNumber());
                        presale.setEmail(dtoPresales.email());
                        presale.setDocumentType(dtoPresales.documentTypeEnum());
                        presale.setDocumentNumber(dtoPresales.documentNumber());

                        presalesRepository.save(presale);

                        EditPresalesResponseDto response = new EditPresalesResponseDto(
                                "presales edited successfully");

                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("presales not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("presales not exists by id cause!"));
        }
    }

    public Result<ListPresalesResponseDto, Exception> listPresales() {
        List<PresalesEntity> presalesEntities = presalesRepository.findAll();

        try {

            List<PresalesDto> presalesDtos = presalesEntities.stream()
                    .map(presales -> new PresalesDto(presales.getId(), presales.getName(), presales.getPhoneNumber(),
                            presales.getEmail(), presales.getDocumentType(), presales.getDocumentNumber(),
                            presales.getUser().getId(), presales.getDistributor().getId()))
                    .toList();

            return Result.ok(new ListPresalesResponseDto(presalesDtos, "presales found successfully!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving presales! ", e));
        }
    }

    public Result<PresalesResponseDto, Exception> showPresale(Long id) {
        try {
            return presalesRepository.findById(id).map(presale -> {
                PresalesDto presalesDto = new PresalesDto(presale.getId(), presale.getName(), presale.getPhoneNumber(),
                        presale.getEmail(), presale.getDocumentType(), presale.getDocumentNumber(),
                        presale.getUser().getId(), presale.getDistributor().getId());

                return Result.ok(new PresalesResponseDto(presalesDto, "presales found successfully"));

            }).orElseThrow();

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving presales!"));
        }
    }

    public void assignPresalesToDistributor(PresalesEntity presalesEntity) {
        try {
            System.out.println("=== INICIO assignPresalesToDistributor ===");

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("Username autenticado: " + username);

            var userOpt = userRepository.findByUsername(username);
            if (userOpt.isEmpty()) {
                System.out.println("ERROR: User not found con username: " + username);
                return;
            }

            UserEntity user = userOpt.get();
            System.out.println("User encontrado - ID: " + user.getId() + ", Username: " + user.getUsername());
            System.out.println("Roles del usuario: " + user.getRoles());

            System.out.println("Buscando distribuidor con user_id: " + user.getId());
            var distributorOpt = distributorRepository.findByUser_Id(user.getId());

            if (distributorOpt.isEmpty()) {
                System.out.println("ERROR: Distributor NO encontrado para user_id: " + user.getId());
                System.out.println(
                        "Verifica en la BD si existe un registro en 'distributors' con user_id = " + user.getId());
                return;
            }

            DistributorEntity distributorAuthenticated = distributorOpt.get();
            System.out.println("Distribuidor encontrado - ID: " + distributorAuthenticated.getId() +
                    ", Nombre: " + distributorAuthenticated.getName() +
                    ", User_ID: " + distributorAuthenticated.getUser().getId());

            presalesEntity.setDistributor(distributorAuthenticated);
            System.out.println("Distribuidor asignado exitosamente al presales");
            System.out.println("=== FIN assignPresalesToDistributor ===");

        } catch (Exception e) {
            System.out.println("EXCEPCIÓN en assignPresalesToDistributor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
