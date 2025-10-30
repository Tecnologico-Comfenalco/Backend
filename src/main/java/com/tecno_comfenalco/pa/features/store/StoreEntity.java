package com.tecno_comfenalco.pa.features.store;

import java.util.List;

import com.tecno_comfenalco.pa.features.routes.presales.PresalesRoutesEntity;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.shared.dto.DirectionDto;
import com.tecno_comfenalco.pa.shared.enums.StoreClaimStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores")
@Data
public class StoreEntity {

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

    // Usuario propietario (opcional: puede ser null si la tienda fue registrada por
    // una distribuidora)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, unique = true)
    private UserEntity user;

    // Estado de la tienda: PENDING_CLAIM, CLAIMED, SELF_REGISTERED
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    private StoreClaimStatus claimStatus = StoreClaimStatus.PENDING_CLAIM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presales_route_id", referencedColumnName = "id")
    private PresalesRoutesEntity presalesRoute;

    @OneToMany(mappedBy = "store")
    private List<StoresDistributorsEntity> storesDistributors;
}
