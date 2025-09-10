package com.tecno_comfenalco.pa.features.ventas.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.ventas.SellEntity;
import com.tecno_comfenalco.pa.features.ventas.repository.ISellRepository;

@Profile("postgres")
public interface IPostgresSellRepository extends JpaRepository<SellEntity, Long>, ISellRepository {

}
