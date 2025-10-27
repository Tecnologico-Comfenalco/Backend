package com.tecno_comfenalco.pa.features.presales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;

@NoRepositoryBean
public interface IPresalesRepository extends Repository<PresalesEntity, Long> {

    Optional<PresalesEntity> findById(Long id);

    Optional<PresalesEntity> findByName(String name);

    Optional<PresalesEntity> findByUser_Id(Long userId);

    List<PresalesEntity> findAll();

    PresalesEntity save(PresalesEntity presalesEntity);

    boolean existsByDocumentNumber(Long documentNumber);
}
