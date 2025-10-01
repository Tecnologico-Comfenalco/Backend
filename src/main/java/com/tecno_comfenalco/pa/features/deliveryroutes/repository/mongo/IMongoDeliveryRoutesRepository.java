package com.tecno_comfenalco.pa.features.deliveryroutes.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.deliveryroutes.repository.IRoutesRepository;

@Profile("mongo")
public interface IMongoDeliveryRoutesRepository extends MongoRepository<DeliveryEntity, Long>,
        IRoutesRepository<DeliveryEntity> {

}
