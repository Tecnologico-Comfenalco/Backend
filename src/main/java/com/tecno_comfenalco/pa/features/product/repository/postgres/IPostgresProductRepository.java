package com.tecno_comfenalco.pa.features.product.repository.postgres;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;

@Profile("postgres")
public interface IPostgresProductRepository extends JpaRepository<ProductEntity, UUID>, IProductRepository {

}
