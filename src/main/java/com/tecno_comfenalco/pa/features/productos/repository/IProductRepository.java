package com.tecno_comfenalco.pa.features.productos.repository;

import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.productos.ProductEntity;

@NoRepositoryBean
public interface IProductRepository extends Repository<ProductEntity, UUID> {

}
