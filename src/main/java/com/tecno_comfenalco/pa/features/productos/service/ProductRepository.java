package com.tecno_comfenalco.pa.features.productos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.productos.repository.IProductRepository;

public class ProductRepository {
    @Autowired
    private IProductRepository productRepository;
}
