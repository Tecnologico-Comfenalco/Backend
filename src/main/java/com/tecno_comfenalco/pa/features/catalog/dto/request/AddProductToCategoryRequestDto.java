package com.tecno_comfenalco.pa.features.catalog.dto.request;

import com.tecno_comfenalco.pa.features.product.ProductEntity.Unit;

public record AddProductToCategoryRequestDto(String name, Double price, Unit unit, Long categoryId) {

}
