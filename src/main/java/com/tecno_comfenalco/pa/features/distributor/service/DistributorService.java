package com.tecno_comfenalco.pa.features.distributor.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.dto.DistributorDto;
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
    @Autowired
    private IDistributorRepository distributorRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ICatalogRepository catalogRepository;

    @Autowired
    private IUserRepository userRepository;

    public Result<RegisterDistributorResponseDto, Exception> newDistributor(
            RegisterDistributorRequestDto dtoDistributor) {

        boolean existsDistributor = distributorRepository.existsByName(dtoDistributor.name());

        if (existsDistributor) {
            return Result.error(new Exception("Distributor already register!"));
        }

        try {

            DistributorEntity distributorEntity = new DistributorEntity();
            distributorEntity.setNIT(dtoDistributor.NIT());
            distributorEntity.setName(dtoDistributor.name());
            distributorEntity.setPhoneNumber(dtoDistributor.phoneNumber());
            distributorEntity.setEmail(dtoDistributor.email());
            distributorEntity.setDirection(dtoDistributor.direction());

            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoDistributor.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("DISTRIBUTOR"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));

            distributorEntity.setUser(userEntity);

            distributorRepository.save(distributorEntity);

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
                        distributor.setName(
                                dtoDistributor.name().isBlank() ? distributor.getName() : dtoDistributor.name());
                        distributor.setPhoneNumber(dtoDistributor.phoneNumber().isBlank() ? distributor.getPhoneNumber()
                                : dtoDistributor.phoneNumber());
                        distributor.setEmail(
                                dtoDistributor.email().isBlank() ? distributor.getEmail() : dtoDistributor.email());
                        distributor.setDirection(dtoDistributor.direction().equals(null) ? distributor.getDirection()
                                : dtoDistributor.direction());

                        distributorRepository.save(distributor);

                        EditDistributorResponseDto response = new EditDistributorResponseDto(
                                "Distributor edited succesfull");

                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("Distributor not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("Error editing distributor!", e));
        }
    }

    public Result<ListDistributorsResponseDto, Exception> listDistributors() {
        List<DistributorEntity> distributorEntities = distributorRepository.findAll();

        try {

            List<DistributorDto> distributorDtos = distributorEntities.stream()
                    .map(distributor -> new DistributorDto(distributor.getId(), distributor.getNIT(),
                            distributor.getName(), distributor.getPhoneNumber(), distributor.getEmail(),
                            distributor.getDirection()))
                    .toList();

            return Result.ok(new ListDistributorsResponseDto(distributorDtos, "Distributors found succesfull!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving distributors!"));
        }
    }

    public Result<DistributorResponseDto, Exception> showDistributor(Long id) {
        try {
            return distributorRepository.findById(id).map(distributor -> {
                DistributorDto distributorDto = new DistributorDto(distributor.getId(), distributor.getNIT(),
                        distributor.getName(), distributor.getPhoneNumber(), distributor.getEmail(),
                        distributor.getDirection());

                return Result.ok(new DistributorResponseDto(distributorDto, "Distributor found succesfull"));

            }).orElseThrow();

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving Distributor!"));
        }
    }

    public Result<DistributorResponseDto, Exception> showDistributorById(Long NIT) {
        try {
            return distributorRepository.findByNIT(NIT).map(distributor -> {
                DistributorDto distributorDto = new DistributorDto(distributor.getId(), distributor.getNIT(),
                        distributor.getName(), distributor.getPhoneNumber(), distributor.getEmail(),
                        distributor.getDirection());

                return Result.ok(new DistributorResponseDto(distributorDto, "Distributor found succesfull"));

            }).orElseThrow();

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving Distributor!"));
        }
    }
}