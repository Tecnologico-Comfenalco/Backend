package com.tecno_comfenalco.pa.features.store.dto.request;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterStoreRequestDto(
                @NotNull(message = "El NIT de la tienda no puede ser nulo!") @Positive(message = "El NIT de la tienda debe ser positivo!") Long NIT,
                @NotBlank(message = "El nombre de la tienda no puede ser nulo ni vacio!") String name,
                @NotBlank(message = "El telefono no debe ser nulo ni vacio!") String phoneNumber,
                @Email(message = "El correo debe tener un formato correcto!") @NotBlank(message = "El email no debe ser nulo ni vacio!") String email,
                DirectionDto direction) {

}
