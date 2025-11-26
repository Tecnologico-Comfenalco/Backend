package com.tecno_comfenalco.pa.features.catalog;

import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "category", "product" })
@ToString(exclude = { "category", "product" })
@Entity
@Table(name = "categories_products")
public class ProductsCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product; // Asumiendo que tienes una ProductEntity
}
