package com.tecno_comfenalco.pa.features.presales.dto;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

public record PresalesDto(Long id, String name, String phoneNumber, String email, DocumentTypeEnum documentTypeEnum,
        Long documentNumber) {

}
