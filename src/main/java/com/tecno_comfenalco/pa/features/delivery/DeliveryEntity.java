package com.tecno_comfenalco.pa.features.delivery;

import java.util.List;

import com.tecno_comfenalco.pa.features.routes.deliveries.DeliveryRoutesEntity;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

@Data
@Entity
@Table(name = "deliveries")
public class DeliveryEntity {
    public enum LicenseTypeEnum {
        A1, A2, A3, A4, B1, B2, B3, C1, C2, C3, C4, D1, D2, D3, D4
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum documentType;

    private Long documentNumber;
    private String phoneNumber;

    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private LicenseTypeEnum licenseType;

    @OneToMany(mappedBy = "delivery")
    private List<DeliveryRoutesEntity> routes;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id")
    private DistributorEntity distributor;
}
