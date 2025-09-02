package com.tecno_comfenalco.pa.features.entregadores.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.entregadores.DeliveryEntity;

@NoRepositoryBean
public interface IDeliveryRepository extends Repository<DeliveryEntity, Long> {

}
