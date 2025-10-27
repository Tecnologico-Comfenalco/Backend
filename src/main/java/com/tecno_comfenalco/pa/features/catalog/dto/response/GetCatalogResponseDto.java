package com.tecno_comfenalco.pa.features.catalog.dto.response;

import java.util.List;

public record GetCatalogResponseDto(
        Long catalogId,
        Long distributorId,
        String distributorName,
        List<CategoryDto> categories) {
    public record CategoryDto(
            Long categoryId,
            String categoryName,
            int productCount) {
    }
}
