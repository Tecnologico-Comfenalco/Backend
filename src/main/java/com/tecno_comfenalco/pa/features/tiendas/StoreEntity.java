package com.tecno_comfenalco.pa.features.tiendas;

import java.util.Set;

import com.tecno_comfenalco.pa.features.rutas.proveedores.PresalesRoutesEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores")
@Data
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NIT;

    private String name;
    private String direccion;
    private String city;
    private Long cellphone;
    private String email;

    @ManyToMany(mappedBy = "storeEntities")
    private Set<PresalesRoutesEntity> presalesRoutesEntities;
}
