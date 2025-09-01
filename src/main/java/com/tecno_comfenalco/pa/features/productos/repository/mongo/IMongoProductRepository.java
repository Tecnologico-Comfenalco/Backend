package com.tecno_comfenalco.pa.features.productos.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.productos.ProductEntity;
import com.tecno_comfenalco.pa.features.productos.repository.IProductRepository;

@Profile("mongo")
public interface IMongoProductRepository extends MongoRepository<ProductEntity, Long>, IProductRepository {

}
