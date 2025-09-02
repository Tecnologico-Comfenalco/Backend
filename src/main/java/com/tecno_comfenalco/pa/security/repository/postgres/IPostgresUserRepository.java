package com.tecno_comfenalco.pa.security.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;

@Profile("postgres")
public interface IPostgresUserRepository extends JpaRepository<UserEntity, Long>, IUserRepository {

}
