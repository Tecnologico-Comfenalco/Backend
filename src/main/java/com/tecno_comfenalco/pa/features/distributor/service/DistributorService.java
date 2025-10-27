package com.tecno_comfenalco.pa.features.distributor.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.dto.DistributorDto;
import com.tecno_comfenalco.pa.features.distributor.dto.mapper.DistributorMapper;
import com.tecno_comfenalco.pa.features.distributor.dto.request.EditDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.DistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.EditDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.ListDistributorsResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.security.AuthenticationService;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class DistributorService {
    //Declaracion de dependencias como final para inyeccion de dependencias.
    private final IDistributorRepository distributorRepository;
    private final AuthenticationService authenticationService;
    private final IUserRepository userRepository;
    private final ICatalogRepository catalogRepository;

    private final DistributorMapper distributorMapper; // Mapper para Distributor.

    //Constructor para inyeccion de dependencias.
    public DistributorService(IDistributorRepository distributorRepository, AuthenticationService authenticationService,IUserRepository userRepository, ICatalogRepository catalogRepository,DistributorMapper distributorMapper) {
        this.distributorRepository = distributorRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
        this.distributorMapper = distributorMapper;
    }

    public Result<RegisterDistributorResponseDto, Exception> newDistributor(
            RegisterDistributorRequestDto dtoDistributor) {

        boolean existsDistributor = distributorRepository.existsByName(dtoDistributor.name());

        if (existsDistributor) {
            return Result.error(new Exception("Distributor already register!"));
        }

        try {
            //Usamos el mapper para convertir el dto a entidad.
            DistributorDto baseDto = new DistributorDto(null,
                dtoDistributor.NIT(),
                dtoDistributor.name(),
                dtoDistributor.phoneNumber(),
                dtoDistributor.email(),
                dtoDistributor.direction()
                    
            );

            DistributorEntity distributorEntity = distributorMapper.toEntity(baseDto);
            
            //Crear el usuario asociado al distribuidor.
            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoDistributor.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("DISTRIBUTOR"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));

            //Relacionar el usuario con el distribuidor.
            distributorEntity.setUser(userEntity);

            distributorRepository.save(distributorEntity);

            //Crear el catalogo asociado al distribuidor.
            var catalogOfDistributor = new CatalogEntity();
            catalogOfDistributor.setDistributor(distributorEntity);
            catalogRepository.save(catalogOfDistributor);

            return Result.ok(new RegisterDistributorResponseDto("Distributor register succesfull!"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error(new Exception("Error to register distributor!"));
        }

    }

    public Result<EditDistributorResponseDto, Exception> editDistributor(Long id,
            EditDistributorRequestDto dtoDistributor) {
        try {
            return distributorRepository.findById(id)
                    .map(distributor -> {
                        //Crear dto de actualizacion.
                        DistributorDto updateDto = new DistributorDto(
                            id,
                            dtoDistributor.NIT(),
                            dtoDistributor.name(),
                            dtoDistributor.phoneNumber(),
                            dtoDistributor.email(),
                            dtoDistributor.direction()
                        );

                        //Aplicar actualizacion con el mapper.
                        distributorMapper.updateEntityFromDto(updateDto, distributor);

                        distributorRepository.save(distributor);

                        EditDistributorResponseDto response = new EditDistributorResponseDto(
                                "Distributor edited succesfull");

                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("Distributor not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("Distributor not exists by id cause!"));
        }
    }

    public Result<ListDistributorsResponseDto, Exception> listDistributors() {
        List<DistributorEntity> distributorEntities = distributorRepository.findAll();

        try {
            //Reemplamoz el stream/map por el uso del mapper para listas.
            List<DistributorDto> distributorDtos = distributorMapper.toDto(distributorEntities);

            ListDistributorsResponseDto responseDto = new ListDistributorsResponseDto(distributorDtos,
                    "Distributors retrieved succesfull");

            return Result.ok(responseDto);
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving distributors!"));
        }
    }

    public Result<DistributorResponseDto, Exception> showDistributor(Long id) {
        try {
            return distributorRepository.findById(id).map(distributor -> {
                DistributorDto distributorDto = distributorMapper.toDto(distributor);

                DistributorResponseDto responseDto = new DistributorResponseDto(distributorDto,
                        "Distributor retrieved succesfull");

                return Result.ok(responseDto);

            }).orElseGet(() -> Result.error(new Exception("Distributor not found!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving Distributor!"));
        }
    }
}