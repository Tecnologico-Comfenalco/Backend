package com.tecno_comfenalco.pa.features.vehicle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;

@NoRepositoryBean
public interface IVehicleRepository extends Repository<VehicleEntity, Long> {
    VehicleEntity save(VehicleEntity vehicleEntity);

    Optional<VehicleEntity> findById(Long id);

    Optional<VehicleEntity> findByVehiclePlate(String plate);

    List<VehicleEntity> findAll();

    void deleteById(Long id);
}
