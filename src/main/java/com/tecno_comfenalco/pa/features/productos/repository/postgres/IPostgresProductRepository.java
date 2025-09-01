package com.tecno_comfenalco.pa.features.productos.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.productos.ProductEntity;
import com.tecno_comfenalco.pa.features.productos.repository.IProductRepository;

@Profile("postgres")
public interface IPostgresProductRepository extends JpaRepository<ProductEntity, Long>, IProductRepository {

}
