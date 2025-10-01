package com.tecno_comfenalco.pa.features.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;

public class CatalogService {

    @Autowired
    private ICatalogRepository catalogRepository;
}
