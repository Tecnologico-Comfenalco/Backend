package com.tecno_comfenalco.pa.features.category.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.category.repository.ICategoryRepository;

@Profile("postgres")
public interface IPostgresCategoryRepository extends JpaRepository<CategoryEntity, Long>, ICategoryRepository {

}
