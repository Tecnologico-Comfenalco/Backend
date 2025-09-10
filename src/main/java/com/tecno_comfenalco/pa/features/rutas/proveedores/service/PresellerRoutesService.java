package com.tecno_comfenalco.pa.features.rutas.proveedores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.rutas.repository.IRoutesRepository;

@Service
public class PresellerRoutesService {
    @Autowired
    IRoutesRepository<PresalesEntity> routesRepository;

}
