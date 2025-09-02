package com.tecno_comfenalco.pa.features.proveedores.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;

@NoRepositoryBean
public interface IPresalesRepository extends Repository<PresalesEntity, Long> {

}
