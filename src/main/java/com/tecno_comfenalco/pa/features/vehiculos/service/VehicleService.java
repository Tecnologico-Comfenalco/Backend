package com.tecno_comfenalco.pa.features.vehiculos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.vehiculos.repository.IVehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;
}
