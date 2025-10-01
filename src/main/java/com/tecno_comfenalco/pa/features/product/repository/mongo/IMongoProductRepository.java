package com.tecno_comfenalco.pa.features.product.repository.mongo;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;

@Profile("mongo")
public interface IMongoProductRepository extends MongoRepository<ProductEntity, UUID>, IProductRepository {

}
