package com.tecno_comfenalco.pa.features.vehiculos.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.vehiculos.VehicleEntity;

@NoRepositoryBean
public interface IVehicleRepository extends Repository<VehicleEntity, Long> {

}
