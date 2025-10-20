package com.tecno_comfenalco.pa.features.distributor.dto.request;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterDistributorRequestDto(
        @NotBlank(message = "El nit de la distribuidora no puede ser nulo o vacio!") @Positive(message = "El numero debe ser positivo!") Long NIT,
        @NotNull(message = "El nombre de la distribuidora no puede ser nulo!") String name,
        @NotBlank(message = "El numero de telefono no puede ser nulo o vacio!") String phoneNumber,
        @Email(message = "Debe tener un formato correcto de email!") String email, DirectionDto direction) {

}
