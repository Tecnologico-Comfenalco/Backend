package com.tecno_comfenalco.pa.features.vehiculos.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.vehiculos.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehiculos.repository.IVehicleRepository;

@Profile("postgres")
public interface IPostgresVehicleRepository extends JpaRepository<VehicleEntity, Long>, IVehicleRepository {

}
