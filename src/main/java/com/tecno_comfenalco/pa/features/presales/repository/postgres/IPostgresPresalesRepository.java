package com.tecno_comfenalco.pa.features.presales.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;

@Profile("postgres")
public interface IPostgresPresalesRepository extends JpaRepository<PresalesEntity, Long>, IPresalesRepository {

}
