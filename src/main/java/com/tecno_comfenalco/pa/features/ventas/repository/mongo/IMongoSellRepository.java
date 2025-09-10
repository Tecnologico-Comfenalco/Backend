package com.tecno_comfenalco.pa.features.ventas.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.ventas.SellEntity;
import com.tecno_comfenalco.pa.features.ventas.repository.ISellRepository;

@Profile("mongo")
public interface IMongoSellRepository extends MongoRepository<SellEntity, Long>, ISellRepository {

}
