package com.tecno_comfenalco.pa.features.deliveryroutes.presalesroutes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.deliveryroutes.repository.IRoutesRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;

@Service
public class PresellerRoutesService {
    @Autowired
    IRoutesRepository<PresalesEntity> routesRepository;

}
