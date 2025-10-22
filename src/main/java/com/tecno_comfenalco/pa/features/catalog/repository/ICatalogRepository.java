package com.tecno_comfenalco.pa.features.catalog.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;

@NoRepositoryBean
public interface ICatalogRepository extends Repository<CatalogEntity, Long> {
    CatalogEntity save(CatalogEntity catalogEntity);

    Optional<CatalogEntity> findById(Long id);
}
