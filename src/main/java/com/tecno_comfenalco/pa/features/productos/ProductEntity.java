package com.tecno_comfenalco.pa.features.productos;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.features.pedidos.OrderEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class ProductEntity {
    public static enum Unit {
        UNIT, KILOGRAM, LITER, METER, PACK, BOX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;
}
