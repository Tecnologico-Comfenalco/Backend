package com.tecno_comfenalco.pa.features.distributor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.service.DistributorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/distributor")
@RequiredArgsConstructor
public class DistributorController {

    private final DistributorService distributorService;

    public ResponseEntity<RegisterDistributorResponseDto> newDistributor(@RequestBody @Valid RegisterDistributorRequestDto dtoDistributor){
        return null;
    }
}
