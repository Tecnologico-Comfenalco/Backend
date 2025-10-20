package com.tecno_comfenalco.pa.features.product.dto;

import java.util.UUID;

public record ProductDto(UUID id, String name, Integer catalogId, Double price, String unit) {

}
