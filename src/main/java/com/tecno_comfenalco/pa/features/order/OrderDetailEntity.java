package com.tecno_comfenalco.pa.features.order;

import com.tecno_comfenalco.pa.features.product.ProductEntity;

// filepath: src/main/java/com/tecno_comfenalco/pa/pedidos/features/OrderDetail.java
import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetailEntity {

    @EmbeddedId
    private OrderDetailIdEmbedded id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private ProductEntity product; // Asumiendo que tienes una ProductEntity

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}