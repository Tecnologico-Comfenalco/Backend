package com.tecno_comfenalco.pa.features.catalogos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.catalogos.CatalogEntity;

public interface ICatalogRepository extends Repository<CatalogEntity, Long> {
    Optional<CatalogEntity> findById(Long id);

    CatalogEntity save(CatalogEntity catalogEntity);

    List<CatalogEntity> findAll();
}
