package com.tecno_comfenalco.pa.features.category;

import java.util.List;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "products", "catalog" })
@ToString(exclude = { "products", "catalog" })
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "categories_products", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;

    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private CatalogEntity catalog;
}
