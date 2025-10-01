package com.tecno_comfenalco.pa.features.catalog.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;

@NoRepositoryBean
public interface ICatalogRepository extends Repository<CatalogEntity, Long> {
}
