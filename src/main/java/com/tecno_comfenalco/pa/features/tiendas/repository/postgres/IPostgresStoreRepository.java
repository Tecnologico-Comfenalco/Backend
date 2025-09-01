package com.tecno_comfenalco.pa.features.tiendas.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.tiendas.StoreEntity;
import com.tecno_comfenalco.pa.features.tiendas.repository.IStoreRepository;

@Profile("postgres")
public interface IPostgresStoreRepository extends JpaRepository<StoreEntity, Long>, IStoreRepository {

}
