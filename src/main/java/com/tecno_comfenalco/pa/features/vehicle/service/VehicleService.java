package com.tecno_comfenalco.pa.features.vehicle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.vehicle.repository.IVehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;
}
