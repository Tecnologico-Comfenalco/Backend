package com.tecno_comfenalco.pa.features.vehiculos.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.vehiculos.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehiculos.repository.IVehicleRepository;

@Profile("mongo")
public interface IMongoVehicleRepository extends MongoRepository<VehicleEntity, Long>, IVehicleRepository {

}
