package com.tecno_comfenalco.pa.features.pedidos.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.pedidos.OrderEntity;
import com.tecno_comfenalco.pa.features.pedidos.repository.IOrderRepository;

@Profile("postgres")
public interface IPostgresOrderRepository extends JpaRepository<OrderEntity, Long>, IOrderRepository {

}
