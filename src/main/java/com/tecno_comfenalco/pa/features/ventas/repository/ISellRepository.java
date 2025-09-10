package com.tecno_comfenalco.pa.features.ventas.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.ventas.SellEntity;

@NoRepositoryBean
public interface ISellRepository extends Repository<SellEntity, Long> {

}
