package com.tecno_comfenalco.pa.features.catalog;

import java.util.List;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class CatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id", nullable = false, unique = true)
    private DistributorEntity distributor;

    @OneToMany(mappedBy = "catalog")
    private List<ProductEntity> products;
}
