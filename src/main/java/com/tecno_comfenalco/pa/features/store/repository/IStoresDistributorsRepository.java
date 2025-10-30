package com.tecno_comfenalco.pa.features.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.store.StoresDistributorsEntity;

public interface IStoresDistributorsRepository extends Repository<StoresDistributorsEntity, Long> {
    StoresDistributorsEntity save(StoresDistributorsEntity entity);
    
    Optional<StoresDistributorsEntity> findByStore_IdAndDistributor_Id(Long storeId, Long distributorId);
    
    List<StoresDistributorsEntity> findByStore_Id(Long storeId);
    
    List<StoresDistributorsEntity> findByDistributor_Id(Long distributorId);
    
    boolean existsByStore_IdAndDistributor_Id(Long storeId, Long distributorId);
}
