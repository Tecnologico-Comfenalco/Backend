package com.tecno_comfenalco.pa.security.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;

@Profile("mongo")
public interface IMongoUserRepository extends MongoRepository<UserEntity, Long>, IUserRepository {

}
