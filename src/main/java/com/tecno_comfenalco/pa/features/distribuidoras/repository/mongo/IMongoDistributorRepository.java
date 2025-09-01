package com.tecno_comfenalco.pa.features.distribuidoras.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.distribuidoras.DistributorEntity;
import com.tecno_comfenalco.pa.features.distribuidoras.repository.IDistributorRepository;

@Profile("mongo")
public interface IMongoDistributorRepository extends MongoRepository<DistributorEntity, Long>, IDistributorRepository {

}
