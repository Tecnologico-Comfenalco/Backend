package com.tecno_comfenalco.pa.features.presales.dto.request;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EditPresalesRequestDto(
        @NotBlank(message = "El nombre del preventista no debe ser nulo ni vacio!") String name,
        @NotBlank(message = "El numero de telefono no puede ser nulo ni vacio!") String phoneNumber,
        @Email(message = "El email debe tener un formato correcto!") String email, DocumentTypeEnum documentTypeEnum,
        @Positive(message = "El numero de documento de identidad debe ser positivo!") @NotBlank(message = "El numero de documento de identidad no debe ser nulo ni vacio!") Long documentNumber) {

}
