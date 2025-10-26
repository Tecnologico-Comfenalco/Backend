package com.tecno_comfenalco.pa.features.store.dto;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record StoreDto(Long NIT, String name, String phoneNumber, String email, DirectionDto direction) {

}
