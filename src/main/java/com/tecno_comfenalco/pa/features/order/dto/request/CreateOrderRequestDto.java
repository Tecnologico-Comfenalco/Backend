package com.tecno_comfenalco.pa.features.order.dto.request;

import java.util.List;

import com.tecno_comfenalco.pa.features.order.dto.OrderProductDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequestDto(
        @NotNull(message = "La id de la tienda no debe ser nulo!") @Positive(message = "La id de la tienda debe ser un numero positivo!") Long store_id,
        @NotNull(message = "La id de la distribuidora no debe ser nulo!") @Positive(message = "La id de la distribuidora debe ser positivo!") Long distributor_id,
        Long presales_id, // Ahora es opcional - puede ser null si la tienda pide directamente
        @NotNull(message = "La lista de productos no debe ser nulo!") List<OrderProductDto> productEntities) {

}
