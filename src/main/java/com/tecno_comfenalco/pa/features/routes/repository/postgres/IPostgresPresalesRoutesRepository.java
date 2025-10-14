package com.tecno_comfenalco.pa.features.routes.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.routes.repository.IRoutesRepository;

@Profile("postgres")
public interface IPostgresPresalesRoutesRepository extends JpaRepository<PresalesEntity, Long>,
        IRoutesRepository<PresalesEntity> {
}