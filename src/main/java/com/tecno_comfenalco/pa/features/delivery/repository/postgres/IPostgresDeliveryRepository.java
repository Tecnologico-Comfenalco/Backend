package com.tecno_comfenalco.pa.features.delivery.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.delivery.repository.IDeliveryRepository;

@Profile("postgres")
public interface IPostgresDeliveryRepository extends JpaRepository<DeliveryEntity, Long>, IDeliveryRepository {

}
