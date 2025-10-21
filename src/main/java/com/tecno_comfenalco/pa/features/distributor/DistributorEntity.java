package com.tecno_comfenalco.pa.features.distributor;

import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "distributors")
public class DistributorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NIT", nullable = false, unique = true)
    private Long NIT;

    private String name;
    private String phoneNumber;
    private String email;

    @Embedded
    private DirectionDto direction;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserEntity user;

}
