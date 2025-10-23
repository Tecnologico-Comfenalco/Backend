package com.tecno_comfenalco.pa.features.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.product.ProductEntity;

@NoRepositoryBean
public interface IProductRepository extends Repository<ProductEntity, UUID> {
    ProductEntity save(ProductEntity productEntity);

    Optional<ProductEntity> findById(UUID id);

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findAll();

    // Search products by the category id stored in the ProductsCatalogEntity linked
    // via 'categoryProduct'
    List<ProductEntity> findByCategoryProduct_Category_Id(Long id);

    boolean existsByName(String name);

    void deleteById(UUID id);
}
