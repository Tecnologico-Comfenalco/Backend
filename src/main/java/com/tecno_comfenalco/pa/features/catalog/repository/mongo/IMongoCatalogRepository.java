package com.tecno_comfenalco.pa.features.catalog.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;

@Profile("mongo")
public interface IMongoCatalogRepository extends MongoRepository<CatalogEntity, Long>, ICatalogRepository {

}
