package com.tecno_comfenalco.pa.features.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.catalog.ProductsCatalogEntity;

public interface IProductsCatalogRepository extends JpaRepository<ProductsCatalogEntity, Long> {
    // Standard CRUD repository for category-product join entity
}
