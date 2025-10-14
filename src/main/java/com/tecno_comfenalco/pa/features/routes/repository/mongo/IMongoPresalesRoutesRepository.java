package com.tecno_comfenalco.pa.features.routes.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.routes.repository.IRoutesRepository;

@Profile("mongo")
public interface IMongoPresalesRoutesRepository
                extends MongoRepository<PresalesEntity, Long>, IRoutesRepository<PresalesEntity> {

}
