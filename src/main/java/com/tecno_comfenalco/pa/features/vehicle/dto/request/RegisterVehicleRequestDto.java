package com.tecno_comfenalco.pa.features.vehicle.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterVehicleRequestDto(
        @NotBlank(message = "La placa del vehiculo no debe ser nulo ni vacio!") String vehiclePlate,
        @NotBlank(message = "") String model,
        @NotBlank(message = "La marca del vehiculo no debe ser nulo!") String brand) {

}
