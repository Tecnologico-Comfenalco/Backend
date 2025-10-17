package com.tecno_comfenalco.pa.features.vehicle;

import java.util.List;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
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
}
