package com.tecno_comfenalco.pa.features.catalogos.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.catalogos.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalogos.repository.ICatalogRepository;

@Profile("postgres")
public interface IPostgresCatalogRepository extends JpaRepository<CatalogEntity, Long>, ICatalogRepository {

}
