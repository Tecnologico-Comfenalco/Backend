package com.tecno_comfenalco.pa.features.catalog.dto.response;

import java.util.List;
import java.util.UUID;

public record GetCategoryProductsResponseDto(
        Long categoryId,
        String categoryName,
        List<ProductDto> products) {
    public record ProductDto(
            UUID productId,
            String productName,
            Double price,
            String unit) {
    }
}
