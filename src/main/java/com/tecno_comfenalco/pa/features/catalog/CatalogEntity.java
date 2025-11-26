package com.tecno_comfenalco.pa.features.catalog;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "distributor" })
@ToString(exclude = { "distributor" })
@Entity
@Table(name = "catalogs")
public class CatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id", nullable = false, unique = true)
    private DistributorEntity distributor;
}
