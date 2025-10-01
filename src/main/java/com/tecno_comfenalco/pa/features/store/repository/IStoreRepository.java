package com.tecno_comfenalco.pa.features.store.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.store.StoreEntity;

@NoRepositoryBean
public interface IStoreRepository extends Repository<StoreEntity, Long> {

}
