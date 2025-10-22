package com.tecno_comfenalco.pa.features.product;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.order.OrderDetailEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {
    public static enum Unit {
        UNIT, KILOGRAM, LITER, METER, PACK, BOX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailEntity> orderDetails;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id", nullable = true)
    private DistributorEntity distributor;
}
