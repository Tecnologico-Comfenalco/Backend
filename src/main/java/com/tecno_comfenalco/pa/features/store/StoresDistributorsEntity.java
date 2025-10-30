package com.tecno_comfenalco.pa.features.store;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores_distributors")
@Data
public class StoresDistributorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    private DistributorEntity distributor;

    /**
     * Código de cliente interno que la distribuidora usa para identificar a esta
     * tienda
     * Útil para sincronización con sistemas ERP externos
     */
    @Column(name = "internal_client_code")
    private String internalClientCode;

}
