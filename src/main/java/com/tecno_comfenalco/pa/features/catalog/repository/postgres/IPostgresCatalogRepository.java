package com.tecno_comfenalco.pa.features.catalog.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;

@Profile("postgres")
public interface IPostgresCatalogRepository extends JpaRepository<CatalogEntity, Long>, ICatalogRepository {

}
