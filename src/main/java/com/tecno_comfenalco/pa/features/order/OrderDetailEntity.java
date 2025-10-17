package com.tecno_comfenalco.pa.features.order;

import com.tecno_comfenalco.pa.features.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

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