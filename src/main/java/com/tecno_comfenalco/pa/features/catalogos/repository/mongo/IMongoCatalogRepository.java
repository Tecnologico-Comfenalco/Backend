package com.tecno_comfenalco.pa.features.catalogos.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.catalogos.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalogos.repository.ICatalogRepository;

@Profile("mongo")
public interface IMongoCatalogRepository extends MongoRepository<CatalogEntity, Long>, ICatalogRepository {

}
