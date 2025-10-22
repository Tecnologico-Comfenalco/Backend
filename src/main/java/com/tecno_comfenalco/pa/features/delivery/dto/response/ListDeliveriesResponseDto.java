package com.tecno_comfenalco.pa.features.delivery.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.delivery.dto.DeliveryDto;

public record ListDeliveriesResponseDto(List<DeliveryDto> deliveries, String message) {

}
