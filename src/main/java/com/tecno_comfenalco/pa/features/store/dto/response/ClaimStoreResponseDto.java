package com.tecno_comfenalco.pa.features.store.dto.response;

public record ClaimStoreResponseDto(
        String message,
        Long storeId,
        Long userId,
        String username) {
}
