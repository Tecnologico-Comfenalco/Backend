package com.tecno_comfenalco.pa.features.category.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.category.repository.ICategoryRepository;

@Profile("mongo")
public interface IMongoCategoryRepository extends MongoRepository<CategoryEntity, Long>, ICategoryRepository {

}
