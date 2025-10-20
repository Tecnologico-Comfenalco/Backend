package com.tecno_comfenalco.pa.features.distributor.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

@NoRepositoryBean
public interface IDistributorRepository extends Repository<DistributorEntity, Long> {

    Optional<DistributorEntity> findById(Long id);

    Optional<DistributorEntity> findByName(String name);

    DistributorEntity save(DistributorEntity distributorEntity);
}
