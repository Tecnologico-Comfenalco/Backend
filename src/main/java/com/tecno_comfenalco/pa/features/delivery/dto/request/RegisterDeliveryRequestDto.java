package com.tecno_comfenalco.pa.features.delivery.dto.request;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity.LicenseTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RegisterDeliveryRequestDto(@NotBlank(message = "El nombre no debe ser nulo ni vacio!") String name,
        DocumentTypeEnum documentTypeEnum,
        @Positive(message = "El numero de documento debe ser positivo!") @NotBlank(message = "El numero de documento no debe ser nulo ni vacio!") Long documentnumber,
        @NotBlank(message = "El telefono no debe ser nulo ni vacio!") String phoneNumber,
        @NotBlank(message = "El numero de licencia no debe ser nulo ni vacio!") String licenseNumber,
        LicenseTypeEnum licenseType) {

}
