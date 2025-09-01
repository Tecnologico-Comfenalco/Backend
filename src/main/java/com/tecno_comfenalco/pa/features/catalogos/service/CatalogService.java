package com.tecno_comfenalco.pa.features.catalogos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.catalogos.repository.ICatalogRepository;

public class CatalogService {

    @Autowired
    private ICatalogRepository catalogRepository;
}
