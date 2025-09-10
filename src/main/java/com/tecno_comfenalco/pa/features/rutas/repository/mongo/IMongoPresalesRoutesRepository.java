package com.tecno_comfenalco.pa.features.rutas.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.rutas.repository.IRoutesRepository;

@Profile("mongo")
public interface IMongoPresalesRoutesRepository
        extends MongoRepository<PresalesEntity, Long>, IRoutesRepository<PresalesEntity> {

}
