package com.tecno_comfenalco.pa.features.vehicle.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;

@NoRepositoryBean
public interface IVehicleRepository extends Repository<VehicleEntity, Long> {

}
