package com.tecno_comfenalco.pa.features.entregadores.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.entregadores.DeliveryEntity;
import com.tecno_comfenalco.pa.features.entregadores.repository.IDeliveryRepository;

@Profile("mongo")
public interface IMongoDeliveryRepository extends JpaRepository<DeliveryEntity, Long>, IDeliveryRepository {

}
