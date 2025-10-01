package com.tecno_comfenalco.pa.features.order.repository.postgres;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.order.OrderEntity;
import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;

@Profile("postgres")
public interface IPostgresOrderRepository extends JpaRepository<OrderEntity, UUID>, IOrderRepository {

}
