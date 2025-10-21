package com.tecno_comfenalco.pa.features.distributor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.dto.DistributorDto;
import com.tecno_comfenalco.pa.features.distributor.dto.request.EditDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.DistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.EditDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.ListDistributorsResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

public class DistributorService {
    @Autowired
    private IDistributorRepository distributorRepository;

    public Result<RegisterDistributorResponseDto, Exception> newDistributor(
            RegisterDistributorRequestDto dtoDistributor) {

        boolean existsDistributor = distributorRepository.existByName(dtoDistributor.name());

        if (existsDistributor) {
            Result.error(new Exception("Distributor already register!"));
        }

        try {

            DistributorEntity distributorEntity = new DistributorEntity();
            distributorEntity.setNIT(dtoDistributor.NIT());
            distributorEntity.setName(dtoDistributor.name());
            distributorEntity.setPhoneNumber(dtoDistributor.phoneNumber());
            distributorEntity.setEmail(dtoDistributor.email());
            distributorEntity.setDirection(dtoDistributor.direction());

            distributorRepository.save(distributorEntity);

            Result.ok(new RegisterDistributorResponseDto("Distributor register succesfull!"));

        } catch (Exception e) {
            return Result.error(new Exception("Error to register distributor!"));
        }

        return null;
    }

    public Result<EditDistributorResponseDto, Exception> editDistributor(Long id,
            EditDistributorRequestDto dtoDistributor) {
        try {
            return distributorRepository.findById(id)
                    .map(distributor -> {
                        distributor.setNIT(dtoDistributor.NIT());
                        distributor.setName(dtoDistributor.name());
                        distributor.setPhoneNumber(dtoDistributor.phoneNumber());
                        distributor.setEmail(dtoDistributor.email());
                        distributor.setDirection(dtoDistributor.direction());

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

            }).orElseGet(() -> Result.error(new Exception("")));

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving Distributor!"));
        }
    }
}
