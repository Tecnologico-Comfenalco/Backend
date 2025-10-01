package com.tecno_comfenalco.pa.features.vehicle.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehicle.repository.IVehicleRepository;

@Profile("postgres")
public interface IPostgresVehicleRepository extends JpaRepository<VehicleEntity, Long>, IVehicleRepository {

}
