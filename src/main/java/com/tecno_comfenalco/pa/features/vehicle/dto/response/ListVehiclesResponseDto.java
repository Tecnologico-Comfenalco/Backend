package com.tecno_comfenalco.pa.features.vehicle.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.vehicle.dto.VehicleDto;

public record ListVehiclesResponseDto(List<VehicleDto> vehicleDtos, String message) {
    
}
