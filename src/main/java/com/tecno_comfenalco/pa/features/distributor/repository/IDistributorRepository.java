package com.tecno_comfenalco.pa.features.distributor.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

@NoRepositoryBean
public interface IDistributorRepository extends Repository<DistributorEntity, Long> {

}
