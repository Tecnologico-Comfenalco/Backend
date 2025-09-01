package com.tecno_comfenalco.pa.features.proveedores.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.proveedores.repository.IPresalesRepository;

@Profile("postgres")
public interface IPostgresPresalesRepository extends JpaRepository<PresalesEntity, Long>, IPresalesRepository {

}
