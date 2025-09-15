package com.tecno_comfenalco.pa.features.rutas.proveedores;

import java.util.Set;

import com.tecno_comfenalco.pa.features.proveedores.PresalesEntity;
import com.tecno_comfenalco.pa.features.tiendas.StoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "presales_routes")
@Data
public class PresalesRoutesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "presales_id", unique = true, nullable = false)
    private PresalesEntity presalesEntityMany;

    @ManyToMany()
    @JoinTable(name = "presales_routes_store", joinColumns = @JoinColumn(name = "presales_routes_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<StoreEntity> storeEntities;
}
