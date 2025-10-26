package com.tecno_comfenalco.pa.features.store.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.store.dto.StoreDto;

public record ListStoresResponseDto(List<StoreDto> storeDtos, String message) {
    
}
