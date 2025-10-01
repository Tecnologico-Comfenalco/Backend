package com.tecno_comfenalco.pa.features.store.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;

public class StoreService {
    @Autowired
    private IStoreRepository storeRepository;
}
