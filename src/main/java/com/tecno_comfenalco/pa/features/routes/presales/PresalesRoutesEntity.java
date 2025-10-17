package com.tecno_comfenalco.pa.features.routes.presales;

import java.util.List;

import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.store.StoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "presales_routes")
public class PresalesRoutesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "presalesRoute")
    private List<StoreEntity> stores;

    @ManyToOne
    @JoinColumn(name = "presales_id", referencedColumnName = "id", nullable = false)
    private PresalesEntity presalesRoute;
}
