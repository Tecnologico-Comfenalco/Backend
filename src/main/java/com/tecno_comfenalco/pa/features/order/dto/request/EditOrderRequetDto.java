package com.tecno_comfenalco.pa.features.order.dto.request;

import java.util.List;

import com.tecno_comfenalco.pa.features.order.dto.OrderProductDto;

import jakarta.validation.constraints.NotNull;

public record EditOrderRequetDto(
                @NotNull(message = "La lista de productos no debe ser nulo!") List<OrderProductDto> productEntities) {
}
