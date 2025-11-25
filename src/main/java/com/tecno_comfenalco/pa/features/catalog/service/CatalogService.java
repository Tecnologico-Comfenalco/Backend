package com.tecno_comfenalco.pa.features.catalog.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.ProductsCatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.dto.response.AddCategoryToCatalogResponseDto;
import com.tecno_comfenalco.pa.features.catalog.dto.response.GetCatalogResponseDto;
import com.tecno_comfenalco.pa.features.catalog.dto.response.GetCategoryProductsResponseDto;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;
import com.tecno_comfenalco.pa.features.catalog.repository.IProductsCatalogRepository;
import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.category.repository.ICategoryRepository;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;
import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.dto.ProductDto;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.security.CustomUserDetails;
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

    @Autowired
    private IDistributorRepository distributorRepository;

    @Autowired
    private IPresalesRepository presalesRepository;

    /**
     * Agrega una categoría al catálogo de la distribuidora autenticada
     * Detecta automáticamente el catálogo del usuario logueado
     */
    public Result<AddCategoryToCatalogResponseDto, Exception> addCategoryToCatalog(String categoryName) {

        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // Buscar la distribuidora asociada al usuario
            Optional<DistributorEntity> distributorOpt = distributorRepository.findByUser_Id(userId);

            if (distributorOpt.isEmpty()) {
                return Result.error(new Exception("Distributor not found for the authenticated user"));
            }

            DistributorEntity distributor = distributorOpt.get();

            // Buscar el catálogo de la distribuidora
            Optional<CatalogEntity> catalogOpt = catalogRepository.findByDistributor_Id(distributor.getId());

            if (catalogOpt.isEmpty()) {
                return Result.error(new Exception("Catalog not found for this distributor"));
            }

            CatalogEntity catalog = catalogOpt.get();

            // Crear la nueva categoría
            CategoryEntity category = new CategoryEntity();
            category.setName(categoryName);
            category.setCatalog(catalog);

            categoryRepository.save(category);

            return Result.ok(new AddCategoryToCatalogResponseDto("Category added successfully"));
        } catch (Exception e) {
            return Result.error(new Exception("Failed to add category to catalog: " + e.getMessage()));
        }
    }

    /**
     * Agrega un producto a una categoría
     * Valida automáticamente que la categoría pertenezca al catálogo de la
     * distribuidora autenticada
     */
    public Result<AddCategoryToCatalogResponseDto, Exception> addProductToCategory(Long categoryId, UUID productId) {
        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // Buscar la distribuidora asociada al usuario
            Optional<DistributorEntity> distributorOpt = distributorRepository.findByUser_Id(userId);

            if (distributorOpt.isEmpty()) {
                return Result.error(new Exception("Distributor not found for the authenticated user"));
            }

            DistributorEntity distributor = distributorOpt.get();

            // Buscar el catálogo de la distribuidora
            Optional<CatalogEntity> catalogOpt = catalogRepository.findByDistributor_Id(distributor.getId());

            if (catalogOpt.isEmpty()) {
                return Result.error(new Exception("Catalog not found for this distributor"));
            }

            CatalogEntity catalog = catalogOpt.get();

            // Buscar la categoría
            Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);

            if (categoryOpt.isEmpty()) {
                return Result.error(new Exception("Category not found"));
            }

            CategoryEntity category = categoryOpt.get();

            // Validar que la categoría pertenezca al catálogo de la distribuidora
            if (!category.getCatalog().getId().equals(catalog.getId())) {
                return Result.error(
                        new Exception("You don't have permission to add products to this category"));
            }

            // Buscar el producto
            Optional<ProductEntity> productOpt = productRepository.findById(productId);

            if (productOpt.isEmpty()) {
                return Result.error(new Exception("Product not found"));
            }

            ProductEntity product = productOpt.get();

            // Crear la relación entre categoría y producto
            ProductsCatalogEntity categoryProduct = new ProductsCatalogEntity();
            categoryProduct.setCategory(category);
            categoryProduct.setProduct(product);

            productsCatalogRepository.save(categoryProduct);

            return Result.ok(new AddCategoryToCatalogResponseDto("Product added successfully"));
        } catch (Exception e) {
            return Result.error(new Exception("Failed to add product to category: " + e.getMessage()));
        }
    }

    /**
     * Obtiene el catálogo de la distribuidora del usuario autenticado
     * Funciona tanto para DISTRIBUTOR como para PRESALES (que pertenecen a una
     * distribuidora)
     */
    public Result<GetCatalogResponseDto, Exception> getCatalogForAuthenticatedUser() {
        try {
            // Obtener usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // 1. Intentar obtener distribuidor por userId
            Optional<DistributorEntity> distributorOpt = distributorRepository.findByUser_Id(userId);

            DistributorEntity distributor;

            if (distributorOpt.isPresent()) {
                // si el usuario es una distribura
                distributor = distributorOpt.get();

            } else {
                // Usuario NO es distribuidor → debe ser preventista
                Optional<PresalesEntity> presalesOpt = presalesRepository.findByUser_Id(userId);

                if (presalesOpt.isEmpty()) {
                    return Result.error(new Exception("User is not associated with a distributor or presales entity."));
                }

                distributor = presalesOpt.get().getDistributor();
            }

            // 2. Buscar el catálogo de la distribuidora
            Optional<CatalogEntity> catalogOpt = catalogRepository.findByDistributor_Id(distributor.getId());

            if (catalogOpt.isEmpty()) {
                return Result.error(new Exception("Catalog not found for this distributor"));
            }

            CatalogEntity catalog = catalogOpt.get();

            // 3. Obtener categorías
            List<CategoryEntity> categories = categoryRepository.findByCatalog_Id(catalog.getId());

            // 4. Mapear a DTO
            List<GetCatalogResponseDto.CategoryDto> categoryDtos = categories.stream()
                    .map(cat -> new GetCatalogResponseDto.CategoryDto(
                            cat.getId(),
                            cat.getName(),
                            cat.getProducts().stream()
                                    .map(pc -> new ProductDto(
                                            pc.getId(),
                                            pc.getName(),
                                            pc.getPrice(),
                                            pc.getUnit().toString(),
                                            pc.getDistributor().getId()))
                                    .collect(Collectors.toList()),
                            cat.getProducts() != null ? cat.getProducts().size() : 0))
                    .collect(Collectors.toList());

            // 5. Construir respuesta
            GetCatalogResponseDto response = new GetCatalogResponseDto(
                    catalog.getId(),
                    distributor.getId(),
                    distributor.getName(),
                    categoryDtos);

            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Failed to get catalog: " + e.getMessage()));
        }
    }

    /**
     * Obtiene los productos de una categoría específica
     * Valida que la categoría pertenezca al catálogo del usuario autenticado
     */
    public Result<GetCategoryProductsResponseDto, Exception> getProductsByCategory(Long categoryId) {
        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // Buscar la distribuidora asociada al usuario
            Optional<DistributorEntity> distributorOpt = distributorRepository.findByUser_Id(userId);

            if (distributorOpt.isEmpty()) {
                return Result.error(new Exception("Distributor not found for the authenticated user"));
            }

            // Buscar la categoría
            Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);

            if (categoryOpt.isEmpty()) {
                return Result.error(new Exception("Category not found"));
            }

            CategoryEntity category = categoryOpt.get();

            // Validar que la categoría pertenezca al catálogo de la distribuidora del
            // usuario
            if (!category.getCatalog().getDistributor().getId().equals(distributorOpt.get().getId())) {
                return Result.error(new Exception("You don't have permission to access this category"));
            }

            // Obtener los productos de la categoría
            List<ProductEntity> products = category.getProducts();

            // Mapear a DTO
            List<GetCategoryProductsResponseDto.ProductDto> productDtos = products.stream()
                    .map(prod -> new GetCategoryProductsResponseDto.ProductDto(
                            prod.getId(),
                            prod.getName(),
                            prod.getPrice(),
                            prod.getUnit().toString()))
                    .collect(Collectors.toList());

            GetCategoryProductsResponseDto response = new GetCategoryProductsResponseDto(
                    category.getId(),
                    category.getName(),
                    productDtos);

            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Failed to get category products: " + e.getMessage()));
        }
    }
}
