package com.tecno_comfenalco.pa.features.distribuidoras.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.distribuidoras.repository.IDistributorRepository;

public class DistributorService {
    @Autowired
    private IDistributorRepository distributorRepository;
}
