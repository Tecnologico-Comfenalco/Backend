package com.tecno_comfenalco.pa.features.catalogos;

import java.util.List;

import com.tecno_comfenalco.pa.features.productos.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogs")
public class CatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "catalogEntity")
    private List<ProductEntity> productEntities;
}
