package com.tecno_comfenalco.pa.features.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.pedidos.repository.IOrderRepository;

public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
}
