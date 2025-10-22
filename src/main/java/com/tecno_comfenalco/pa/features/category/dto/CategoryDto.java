package com.tecno_comfenalco.pa.features.category.dto;

import java.util.List;

import com.tecno_comfenalco.pa.features.product.dto.ProductDto;

public record CategoryDto(Long id, String name, List<ProductDto> products) {

}
