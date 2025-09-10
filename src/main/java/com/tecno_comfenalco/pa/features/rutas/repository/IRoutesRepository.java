package com.tecno_comfenalco.pa.features.rutas.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface IRoutesRepository<T> extends Repository<T, Long> {

}
