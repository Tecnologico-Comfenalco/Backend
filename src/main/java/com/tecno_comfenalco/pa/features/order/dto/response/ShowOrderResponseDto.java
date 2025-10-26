package com.tecno_comfenalco.pa.features.order.dto.response;

import com.tecno_comfenalco.pa.features.order.dto.OrderDto;

public record ShowOrderResponseDto(OrderDto orderDto, String message) {
    
}
