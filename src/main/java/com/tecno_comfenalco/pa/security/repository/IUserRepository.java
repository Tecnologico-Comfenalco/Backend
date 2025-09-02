package com.tecno_comfenalco.pa.security.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.security.domain.UserEntity;

@NoRepositoryBean
public interface IUserRepository extends Repository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
