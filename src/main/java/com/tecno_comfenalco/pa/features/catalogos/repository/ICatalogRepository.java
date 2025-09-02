package com.tecno_comfenalco.pa.features.catalogos.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.catalogos.CatalogEntity;

@NoRepositoryBean
public interface ICatalogRepository extends Repository<CatalogEntity, Long> {
}
