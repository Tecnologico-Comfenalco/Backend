package com.tecno_comfenalco.pa.features.order.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;

public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
}
