package com.tecno_comfenalco.pa.features.presales.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;

@NoRepositoryBean
public interface IPresalesRepository extends Repository<PresalesEntity, Long> {

}
