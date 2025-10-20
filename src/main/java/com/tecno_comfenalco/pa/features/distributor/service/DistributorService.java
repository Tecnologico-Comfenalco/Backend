package com.tecno_comfenalco.pa.features.distributor.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.product.dto.response.RegisterProductResponseDto;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

public class DistributorService {
    @Autowired
    private IDistributorRepository distributorRepository;

    public Result<RegisterDistributorResponseDto, Exception> newDistributor(
            RegisterDistributorRequestDto dtoDistributor) {

        distributorRepository.findByName(dtoDistributor.name()).map(distributor -> {

            

            return Result.ok(new RegisterProductResponseDto(""));
        }).orElseGet(() -> Result.error(new Exception("Distributor not found!")));

        return null;
    }
}
