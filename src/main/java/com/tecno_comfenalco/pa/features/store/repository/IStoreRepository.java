package com.tecno_comfenalco.pa.features.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.store.StoreEntity;

@NoRepositoryBean
public interface IStoreRepository extends Repository<StoreEntity, Long> {
    StoreEntity save(StoreEntity storeEntity);

    Optional<StoreEntity> findById(Long id);

    Optional<StoreEntity> findByNIT(Long NIT);

    Optional<StoreEntity> findByUser_Id(Long userId);

    List<StoreEntity> findAll();

    boolean existsByNIT(Long NIT);

    boolean existsById(Long NIT);

    void deleteById(Long id);
}
