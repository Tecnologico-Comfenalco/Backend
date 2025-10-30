package com.tecno_comfenalco.pa.features.order.dto;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.features.order.OrderEntity.OrderStatus;
import com.tecno_comfenalco.pa.features.presales.dto.PresalesDto;
import com.tecno_comfenalco.pa.features.store.dto.StoreDto;

public record OrderDto(
        UUID id,
        Double iva_percent,
        Double total,
        OrderStatus status,
        StoreDto store,
        PresalesDto presales,
        List<OrderProductDto> orderDetails,
        String internalClientCode, // Código interno del cliente en el ERP de la distribuidora
        Long distributorId // ID de la distribuidora que atiende este pedido
) {

}
