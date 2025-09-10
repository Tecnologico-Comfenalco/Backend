package com.tecno_comfenalco.pa.features.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.ventas.repository.ISellRepository;

@Service
public class SellService {
    @Autowired
    private ISellRepository sellRepository;
}
