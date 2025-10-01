package com.tecno_comfenalco.pa.features.presales.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;

@Profile("mongo")
public interface IMongoPresalesRepository extends MongoRepository<PresalesEntity, Long>, IPresalesRepository {

}
