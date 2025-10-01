package com.tecno_comfenalco.pa.features.store.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;

@Profile("postgres")
public interface IPostgresStoreRepository extends JpaRepository<StoreEntity, Long>, IStoreRepository {

}
