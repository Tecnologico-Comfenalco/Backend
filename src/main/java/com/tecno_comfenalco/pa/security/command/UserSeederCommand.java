package com.tecno_comfenalco.pa.security.command;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;

@Component
public class UserSeederCommand implements CommandLineRunner {

        @Autowired
        private IUserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
                // Seed initial users
                userRepository.save(UserEntity.builder().username("admin").password(passwordEncoder.encode("password"))
                                .enabled(true).roles(Set.of("ADMIN")).build());

                userRepository.save(
                                UserEntity.builder().username("presales").password(passwordEncoder.encode("password"))
                                                .enabled(true).roles(Set.of("PRESALES")).build());

                userRepository.save(UserEntity.builder().username("distributor")
                                .password(passwordEncoder.encode("password")).enabled(true)
                                .roles(Set.of("DISTRIBUTOR")).build());

                System.out.println("🟩____Users seeded____🟩");
        }

}
