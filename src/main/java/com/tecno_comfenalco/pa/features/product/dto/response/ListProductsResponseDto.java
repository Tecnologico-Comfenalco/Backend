package com.tecno_comfenalco.pa.features.product.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.product.dto.ProductDto;

public record ListProductsResponseDto(List<ProductDto> products, String message) {
}
