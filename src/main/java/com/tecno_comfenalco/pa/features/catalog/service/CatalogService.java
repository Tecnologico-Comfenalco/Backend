package com.tecno_comfenalco.pa.features.catalog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.catalog.ProductsCatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.dto.response.AddCategoryToCatalogResponseDto;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;
import com.tecno_comfenalco.pa.features.catalog.repository.IProductsCatalogRepository;
import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.category.repository.ICategoryRepository;
import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class CatalogService {

    @Autowired
    private ICatalogRepository catalogRepository;

    @Autowired
    private IProductsCatalogRepository productsCatalogRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IProductRepository productRepository;

    public Result<AddCategoryToCatalogResponseDto, Exception> addCategoryToCatalog(Long catalogId,
            String categoryName) {

        try {
            var catalogOpt = catalogRepository.findById(catalogId);

            if (catalogOpt.isEmpty()) {
                return Result.error(new Exception("Catalog not found"));
            }

            CategoryEntity category = new CategoryEntity();
            category.setName(categoryName);

            category.setCatalog(catalogOpt.get());

            categoryRepository.save(category);

            return Result.ok(new AddCategoryToCatalogResponseDto("Category added successfully"));
        } catch (Exception e) {
            return Result.error(new Exception("Failed to add category to catalog: " + e.getMessage()));
        }
    }

    public Result<AddCategoryToCatalogResponseDto, Exception> addProductToCategory(Long categoryId, UUID productId) {
        try {

            Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);

            if (categoryOpt.isEmpty()) {
                return Result.error(new Exception("Category not found"));
            }

            Optional<ProductEntity> productOpt = productRepository.findById(productId);

            if (productOpt.isEmpty()) {
                return Result.error(new Exception("Product not found"));
            }

            ProductsCatalogEntity categoryProduct = new ProductsCatalogEntity();
            categoryProduct.setCategory(categoryOpt.get());
            categoryProduct.setProduct(productOpt.get());

            productsCatalogRepository.save(categoryProduct);

            return Result.ok(new AddCategoryToCatalogResponseDto("Product added successfully"));
        } catch (Exception e) {
            return Result.error(new Exception("Failed to add product to category: " + e.getMessage()));
        }
    }
}
