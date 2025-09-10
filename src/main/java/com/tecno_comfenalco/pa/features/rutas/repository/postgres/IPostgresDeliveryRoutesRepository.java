package com.tecno_comfenalco.pa.features.rutas.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.entregadores.DeliveryEntity;
import com.tecno_comfenalco.pa.features.rutas.repository.IRoutesRepository;

public interface IPostgresDeliveryRoutesRepository extends JpaRepository<DeliveryEntity, Long>,
        IRoutesRepository<DeliveryEntity> {

}
