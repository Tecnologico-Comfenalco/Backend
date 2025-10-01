package com.tecno_comfenalco.pa.features.deliveryroutes.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.deliveryroutes.repository.IRoutesRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;

@Profile("mongo")
public interface IMongoPresalesRoutesRepository
        extends MongoRepository<PresalesEntity, Long>, IRoutesRepository<PresalesEntity> {

}
