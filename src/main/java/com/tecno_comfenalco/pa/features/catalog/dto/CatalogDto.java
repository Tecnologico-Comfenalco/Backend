package com.tecno_comfenalco.pa.features.catalog.dto;

import java.util.List;

import com.tecno_comfenalco.pa.features.category.dto.CategoryDto;

public record CatalogDto(Long id, Long distributorId, List<CategoryDto> categories) {

}
