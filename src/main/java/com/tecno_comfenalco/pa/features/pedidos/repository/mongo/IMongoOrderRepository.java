package com.tecno_comfenalco.pa.features.pedidos.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.pedidos.OrderEntity;
import com.tecno_comfenalco.pa.features.pedidos.repository.IOrderRepository;

@Profile("mongo")
public interface IMongoOrderRepository extends MongoRepository<OrderEntity, Long>, IOrderRepository {

}
