package com.tecno_comfenalco.pa.features.presales.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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

public class PresalesService {

    @Autowired
    private IPresalesRepository presalesRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserRepository userRepository;

    public Result<RegisterPresalesResponseDto, Exception> newPresales(RegisterPresalesRequestDto dtoPresales) {

        boolean existsPresale = presalesRepository.existsByDocumentNumber(dtoPresales.documentNumber());

        if (existsPresale) {
            return Result.error(new Exception("the current presale is already register!"));
        }

        try {

            PresalesEntity presalesEntity = new PresalesEntity();
            presalesEntity.setName(dtoPresales.name());
            presalesEntity.setPhoneNumber(dtoPresales.phoneNumber());
            presalesEntity.setEmail(dtoPresales.email());
            presalesEntity.setDocumentType(dtoPresales.documentTypeEnum());
            presalesEntity.setDocumentNumber(dtoPresales.documentNumber());

            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoPresales.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("PRESALES"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));

            presalesEntity.setUser(userEntity);
            presalesRepository.save(presalesEntity);

            return Result.ok(new RegisterPresalesResponseDto("Presale register succesfull!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error to register Presale!"));
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
                                "presale edited succesfull");

                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("presale not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("presale not exists by id cause!"));
        }
    }

    public Result<ListPresalesResponseDto, Exception> listPresales() {
        List<PresalesEntity> presalesEntities = presalesRepository.findAll();

        try {

            List<PresalesDto> presalesDtos = presalesEntities.stream()
                    .map(presales -> new PresalesDto(presales.getId(), presales.getName(), presales.getPhoneNumber(),
                            presales.getEmail(), presales.getDocumentType(), presales.getDocumentNumber()))
                    .toList();

            return Result.ok(new ListPresalesResponseDto(presalesDtos, "presales founds succesfull!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving presales!"));
        }
    }

    public Result<PresalesResponseDto, Exception> showPresale(Long id) {
        try {
            return presalesRepository.findById(id).map(presale -> {
                PresalesDto presalesDto = new PresalesDto(presale.getId(), presale.getName(), presale.getPhoneNumber(),
                        presale.getEmail(), presale.getDocumentType(), presale.getDocumentNumber());

                return Result.ok(new PresalesResponseDto(presalesDto, "presale found succesfull"));

            }).orElseThrow();

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving presale!"));
        }
    }
}
