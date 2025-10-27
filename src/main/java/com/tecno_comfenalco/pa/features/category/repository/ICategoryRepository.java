package com.tecno_comfenalco.pa.features.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.category.CategoryEntity;

@NoRepositoryBean
public interface ICategoryRepository extends Repository<CategoryEntity, Long> {
    CategoryEntity save(CategoryEntity categoryEntity);

    Optional<CategoryEntity> findById(Long id);

    List<CategoryEntity> findByCatalog_Id(Long catalogId);
}
