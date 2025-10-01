package com.tecno_comfenalco.pa.features.deliveryroutes.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.deliveryroutes.repository.IRoutesRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;

@Profile("postgres")
public interface IPostgresPresalesRoutesRepository extends JpaRepository<PresalesEntity, Long>,
                IRoutesRepository<PresalesEntity> {
}