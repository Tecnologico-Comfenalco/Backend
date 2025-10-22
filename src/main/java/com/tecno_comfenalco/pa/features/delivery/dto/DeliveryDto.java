package com.tecno_comfenalco.pa.features.delivery.dto;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity.LicenseTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

public record DeliveryDto(Long id, String name, DocumentTypeEnum documentType, Long documentNumber, String phoneNumber,
                String licenseNumber, LicenseTypeEnum licenseType, Long userId, Long distributorId) {

}
