package com.tecno_comfenalco.pa.features.order.repository;

import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.order.OrderEntity;

@NoRepositoryBean
public interface IOrderRepository extends Repository<OrderEntity, UUID> {

}
