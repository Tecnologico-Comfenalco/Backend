package com.tecno_comfenalco.pa.features.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.vehicle.dto.request.RegisterVehicleRequestDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.DisableVehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.ListVehiclesResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.RegisterVehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.VehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.service.VehicleService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicles")
@PreAuthorize("hasAnyRole('ADMIN', 'DISTRIBUTOR')")
public class VehicleController {

    @Autowired
    public VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<RegisterVehicleResponseDto> newVehicle(
            @RequestBody @Valid RegisterVehicleRequestDto dtoVehicle) {
        Result<RegisterVehicleResponseDto, Exception> result = vehicleService.newVehicle(dtoVehicle);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListVehiclesResponseDto> listVehicle() {
        Result<ListVehiclesResponseDto, Exception> result = vehicleService.listAllVehicles();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> showVehicle(@PathVariable Long id) {
        Result<VehicleResponseDto, Exception> result = vehicleService.showVehicle(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DisableVehicleResponseDto> disableVehicle(@PathVariable Long id) {
        Result<DisableVehicleResponseDto, Exception> result = vehicleService.disableVehicle(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
