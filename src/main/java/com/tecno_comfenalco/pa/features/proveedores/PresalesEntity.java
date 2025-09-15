package com.tecno_comfenalco.pa.features.proveedores;

import java.util.List;

import com.tecno_comfenalco.pa.features.rutas.proveedores.PresalesRoutesEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "presales")
@Data
public class PresalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String document_type;
    private Long document_number;
    private Long cellphone;
    private String email;
    
    @OneToMany(mappedBy = "presalesEntityMany")
    private List<PresalesRoutesEntity> presellerRoutesEntityMany;
}
