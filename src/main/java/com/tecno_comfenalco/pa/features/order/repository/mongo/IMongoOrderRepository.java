package com.tecno_comfenalco.pa.features.order.repository.mongo;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.order.OrderEntity;
import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;

@Profile("mongo")
public interface IMongoOrderRepository extends MongoRepository<OrderEntity, UUID>, IOrderRepository {

}
