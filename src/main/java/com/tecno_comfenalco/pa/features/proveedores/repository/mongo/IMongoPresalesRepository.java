package com.tecno_comfenalco.pa.features.proveedores.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.proveedores.repository.IPresalesRepository;

@Profile("mongo")
public interface IMongoPresalesRepository extends MongoRepository<PresalesEntity, Long>, IPresalesRepository {

}
