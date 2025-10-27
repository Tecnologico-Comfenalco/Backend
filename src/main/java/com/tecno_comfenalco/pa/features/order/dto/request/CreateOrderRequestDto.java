package com.tecno_comfenalco.pa.features.order.dto.request;

import java.util.List;

import com.tecno_comfenalco.pa.features.order.dto.OrderProductDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequestDto(
                @NotNull(message = "La id de la tienda no debe ser nulo!") @Positive(message = "La id de la tienda debe ser un numero positivo!") Long store_id,
                @NotNull(message = "La id del preventista no debe ser nulo!") @Positive(message = "La id del preventista debe ser positivo!") Long presales_id,
                @NotNull(message = "La lista de productos no debe ser nulo!") List<OrderProductDto> productEntities) {

}
