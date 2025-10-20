package com.tecno_comfenalco.pa.features.product.dto.request;

import com.tecno_comfenalco.pa.features.product.ProductEntity.Unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EditProductRequestDto(@NotBlank(message = "El nombre no puede estar vacio o nulo!") String name,
        @NotNull(message = "EL category_id no puede ser nulo!") @Positive(message = "El category_id no puede ser negativo!") Integer category_id,
        @NotNull(message = "El precio del producto no puede ser nulo!") @Positive(message = "El precio del producto no puede ser negativo") Double price,
        Unit unit) {

}
