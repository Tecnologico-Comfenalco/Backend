package com.tecno_comfenalco.pa.features.deliveryroutes.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.deliveryroutes.repository.IRoutesRepository;

public interface IPostgresDeliveryRoutesRepository extends JpaRepository<DeliveryEntity, Long>,
        IRoutesRepository<DeliveryEntity> {

}
