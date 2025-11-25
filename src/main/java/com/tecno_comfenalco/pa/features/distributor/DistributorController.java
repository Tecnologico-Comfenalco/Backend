package com.tecno_comfenalco.pa.features.distributor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.distributor.dto.request.EditDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.DistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.EditDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.ListDistributorsResponseDto;
import com.tecno_comfenalco.pa.features.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.features.distributor.service.DistributorService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/distributors")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDistributorResponseDto> newDistributor(
            @RequestBody @Valid RegisterDistributorRequestDto dtoDistributor) {
        Result<RegisterDistributorResponseDto, Exception> result = distributorService.newDistributor(dtoDistributor);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditDistributorResponseDto> editDistributor(@PathVariable Long id,
            @RequestBody @Valid EditDistributorRequestDto dtoDistributor) {
        Result<EditDistributorResponseDto, Exception> result = distributorService.editDistributor(id, dtoDistributor);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListDistributorsResponseDto> listDistributors() {
        Result<ListDistributorsResponseDto, Exception> result = distributorService.listDistributors();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributorResponseDto> showDistributor(@PathVariable Long id) {
        Result<DistributorResponseDto, Exception> result = distributorService.showDistributor(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/nit/{NIT}")
    public ResponseEntity<DistributorResponseDto> showDistributorByNIT(@PathVariable Long NIT) {
        Result<DistributorResponseDto, Exception> result = distributorService.showDistributorById(NIT);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
