package com.tecno_comfenalco.pa.features.routes.deliveries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.routes.repository.IRoutesRepository;

@Service
public class DeliveryRoutesService {
    @Autowired
    IRoutesRepository<DeliveryEntity> routesRepository;
}
