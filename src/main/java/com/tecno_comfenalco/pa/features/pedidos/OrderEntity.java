package com.tecno_comfenalco.pa.features.pedidos;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.features.comprobantes.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.productos.ProductEntity;
import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.tiendas.StoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double iva_percent;
    private Double total;
    private Integer quantity;

    @ManyToOne
    private StoreEntity store;

    @ManyToOne
    private PresalesEntity presales;

    @ManyToOne
    private PaymentReceiptEntity paymentReceipt;

    @ManyToMany
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;
}
