package com.tecno_comfenalco.pa.features.productos;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductEntity {
    private enum Unit {
        UNIT, KILOGRAM, LITER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Unit unit;
}
