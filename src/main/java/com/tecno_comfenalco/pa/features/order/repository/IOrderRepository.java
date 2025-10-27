package com.tecno_comfenalco.pa.features.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.order.OrderEntity;

@NoRepositoryBean
public interface IOrderRepository extends Repository<OrderEntity, UUID> {
    Optional<OrderEntity> findByid(UUID id);

    OrderEntity save(OrderEntity orderEntity);

    List<OrderEntity> findAll();

    List<OrderEntity> findByStore_Id(Long storeId);

    List<OrderEntity> findByPresales_Id(Long presalesId);

    boolean existsByid(UUID id);

    void deleteById(UUID id);
}
