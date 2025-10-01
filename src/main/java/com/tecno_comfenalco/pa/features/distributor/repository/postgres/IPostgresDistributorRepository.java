package com.tecno_comfenalco.pa.features.distributor.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;

@Profile("postgres")
public interface IPostgresDistributorRepository extends JpaRepository<DistributorEntity, Long>, IDistributorRepository {

}
