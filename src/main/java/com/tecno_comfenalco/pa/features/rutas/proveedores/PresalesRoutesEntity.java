package com.tecno_comfenalco.pa.features.rutas.proveedores;

import java.util.List;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.tiendas.StoreEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class PresalesRoutesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "presalesRoute")
    private List<StoreEntity> presales;

    @ManyToOne
    @JoinColumn(name = "presales_id", referencedColumnName = "id", nullable = false)
    private PresalesEntity presalesRoute;
}
