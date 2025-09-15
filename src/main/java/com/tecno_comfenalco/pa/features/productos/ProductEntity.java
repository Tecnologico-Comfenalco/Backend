package com.tecno_comfenalco.pa.features.productos;

import com.tecno_comfenalco.pa.features.catalogos.CatalogEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float price;
    private Integer unit;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CatalogEntity catalogEntity;
}
