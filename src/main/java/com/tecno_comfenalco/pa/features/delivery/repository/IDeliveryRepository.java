package com.tecno_comfenalco.pa.features.delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;

@NoRepositoryBean
public interface IDeliveryRepository extends Repository<DeliveryEntity, Long> {

    Optional<DeliveryEntity> findById(Long id);

    Optional<DeliveryEntity> findByName(String name);

    List<DeliveryEntity> findAll();

    DeliveryEntity save(DeliveryEntity presalesEntity);

    boolean existsByDocumentNumber(Long documentNumber);
}
