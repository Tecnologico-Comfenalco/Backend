package com.tecno_comfenalco.pa.features.tiendas.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.tiendas.repository.IStoreRepository;

public class StoreService {
    @Autowired
    private IStoreRepository storeRepository;
}
