package com.tecno_comfenalco.pa.features.vehicle;

import java.util.List;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;

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
@Table(name = "vehicles")
@Data
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehiclePlate;
    private String model;
    private String brand;

    @ManyToMany
    @JoinTable(name = "vehicle_delivery", joinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "id"))
    private List<DeliveryEntity> deliveries;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id")
    private DistributorEntity distributor;
}
