package com.tecno_comfenalco.pa.features.order.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.order.dto.OrderDto;

public record ListOrderResponseDto(List<OrderDto> orders, String message) {

}
