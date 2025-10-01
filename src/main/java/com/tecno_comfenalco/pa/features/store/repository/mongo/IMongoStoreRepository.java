package com.tecno_comfenalco.pa.features.store.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;

@Profile("mongo")
public interface IMongoStoreRepository extends MongoRepository<StoreEntity, Long>, IStoreRepository {

}
