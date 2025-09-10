package com.tecno_comfenalco.pa.features.rutas.entregadores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.entregadores.DeliveryEntity;
import com.tecno_comfenalco.pa.features.rutas.repository.IRoutesRepository;

@Service
public class DeliveryRoutesService {
    @Autowired
    IRoutesRepository<DeliveryEntity> routesRepository;
}
