package com.tecno_comfenalco.pa.features.distribuidoras.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.distribuidoras.DistributorEntity;
import com.tecno_comfenalco.pa.features.distribuidoras.repository.IDistributorRepository;

@Profile("postgres")
public interface IPostgresDistributorRepository extends JpaRepository<DistributorEntity, Long>, IDistributorRepository {

}
