package com.tecno_comfenalco.pa.features.pedidos.repository;

import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.pedidos.OrderEntity;

@NoRepositoryBean
public interface IOrderRepository extends Repository<OrderEntity, UUID> {

}
