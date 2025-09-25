package com.tecno_comfenalco.pa.features.pedidos;

// filepath: src/main/java/com/tecno_comfenalco/pa/pedidos/features/OrderDetailId.java
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class OrderDetailIdEmbedded implements Serializable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

}