package com.tecno_comfenalco.pa.features.product.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.product.ProductEntity;

public record ListProductsResponseDto(List<ProductEntity> productEntities) {

}
