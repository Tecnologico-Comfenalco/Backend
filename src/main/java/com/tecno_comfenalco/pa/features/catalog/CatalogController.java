package com.tecno_comfenalco.pa.features.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.catalog.dto.request.AddCategoryToCatalogRequestDto;
import com.tecno_comfenalco.pa.features.catalog.dto.request.AddExistingProductToCategoryRequestDto;
import com.tecno_comfenalco.pa.features.catalog.dto.response.AddCategoryToCatalogResponseDto;
import com.tecno_comfenalco.pa.features.catalog.dto.response.GetCatalogResponseDto;
import com.tecno_comfenalco.pa.features.catalog.dto.response.GetCategoryProductsResponseDto;
import com.tecno_comfenalco.pa.features.catalog.service.CatalogService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    /**
     * Agrega una categoría a un catálogo
     * Solo DISTRIBUTOR puede ejecutar este método
     */
    @PostMapping("/{catalogId}/categories")
    @PreAuthorize("hasRole('DISTRIBUTOR')")
    public ResponseEntity<AddCategoryToCatalogResponseDto> addCategoryToCatalog(
            @PathVariable Long catalogId,
            @RequestBody @Valid AddCategoryToCatalogRequestDto request) {
        Result<AddCategoryToCatalogResponseDto, Exception> result = catalogService.addCategoryToCatalog(catalogId,
                request.name());
        return ResponseEntityHelper.toResponseEntity(result);
    }

    /**
     * Agrega un producto existente a una categoría
     * Solo DISTRIBUTOR puede ejecutar este método
     */
    @PostMapping("/categories/{categoryId}/products")
    @PreAuthorize("hasRole('DISTRIBUTOR')")
    public ResponseEntity<AddCategoryToCatalogResponseDto> addProductToCategory(
            @PathVariable Long categoryId,
            @RequestBody @Valid AddExistingProductToCategoryRequestDto request) {
        Result<AddCategoryToCatalogResponseDto, Exception> result = catalogService.addProductToCategory(categoryId,
                request.productId());
        return ResponseEntityHelper.toResponseEntity(result);
    }

    /**
     * Obtiene el catálogo completo de la distribuidora del usuario autenticado
     * Tanto DISTRIBUTOR como PRESALES pueden ver el catálogo de su distribuidora
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('DISTRIBUTOR', 'PRESALES')")
    public ResponseEntity<GetCatalogResponseDto> getMyCatalog() {
        Result<GetCatalogResponseDto, Exception> result = catalogService.getCatalogForAuthenticatedUser();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    /**
     * Obtiene todos los productos de una categoría específica
     * Tanto DISTRIBUTOR como PRESALES pueden ver, pero solo de su propia
     * distribuidora
     */
    @GetMapping("/categories/{categoryId}/products")
    @PreAuthorize("hasAnyRole('DISTRIBUTOR', 'PRESALES')")
    public ResponseEntity<GetCategoryProductsResponseDto> getProductsByCategory(@PathVariable Long categoryId) {
        Result<GetCategoryProductsResponseDto, Exception> result = catalogService.getProductsByCategory(categoryId);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
