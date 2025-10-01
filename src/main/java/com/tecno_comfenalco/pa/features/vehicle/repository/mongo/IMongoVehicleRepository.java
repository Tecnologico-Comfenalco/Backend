package com.tecno_comfenalco.pa.features.vehicle.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehicle.repository.IVehicleRepository;

@Profile("mongo")
public interface IMongoVehicleRepository extends MongoRepository<VehicleEntity, Long>, IVehicleRepository {

}
