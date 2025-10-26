package com.tecno_comfenalco.pa.features.order.dto;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.features.order.OrderDetailEntity;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.store.StoreEntity;

public record OrderDto(UUID id, Double iva_percent, Double total, StoreEntity store, PresalesEntity presales,
        List<OrderDetailEntity> orderDetails) {

}
