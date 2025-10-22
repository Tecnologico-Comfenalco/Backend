package com.tecno_comfenalco.pa.features.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.dto.ProductDto;
import com.tecno_comfenalco.pa.features.product.dto.request.EditProductRequestDto;
import com.tecno_comfenalco.pa.features.product.dto.request.RegisterProductRequestDto;
import com.tecno_comfenalco.pa.features.product.dto.response.DisableProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.EditProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.ListProductsResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.ProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.RegisterProductResponseDto;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class ProductServices {
    @Autowired
    private IProductRepository productRepository;

    public Result<RegisterProductResponseDto, Exception> saveProducts(RegisterProductRequestDto dtoProduct) {

        boolean existProduct = productRepository.existsByName(dtoProduct.name());

        if (existProduct) {
            Result.error(new Exception("Product already Exists"));
        }

        try {
            // TODO: Create a mapper for this
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(dtoProduct.name());
            productEntity.setPrice(dtoProduct.price());
            productEntity.setUnit(dtoProduct.unit());

            productRepository.save(productEntity);

            return Result.ok(new RegisterProductResponseDto("Producto registrado exitosamente!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error registering product"));
        }
    }

    public Result<EditProductResponseDto, Exception> editProduct(UUID id, EditProductRequestDto dtoProduct) {
        try {
            // TODO: Create a mapper for this
            return productRepository.findById(id)
                    .map(product -> {

                        product.setName(dtoProduct.name());
                        product.setPrice(dtoProduct.price());
                        product.setUnit(dtoProduct.unit());

                        productRepository.save(product);

                        EditProductResponseDto responseDto = new EditProductResponseDto("Product modified succesfull!",
                                id);
                        return Result.ok(responseDto);

                    }).orElseGet(() -> Result.error(new Exception("Product not found!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error editing product"));
        }
    }

    public Result<DisableProductResponseDto, Exception> disabledProduct(UUID id) {
        try {
            // TODO: Create a mapper for this
            return productRepository.findById(id).map(product -> {
                productRepository.deleteById(id);
                return Result.ok(new DisableProductResponseDto("Product deleted succesfull!"));

            }).orElseGet(() -> Result.error(new Exception("Product not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("Error disabling product"));
        }
    }

    public Result<ListProductsResponseDto, Exception> listProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        try {
            // TODO: Create a mapper for this
            List<ProductDto> productDtos = productEntities.stream().map(product -> new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getCategory() != null ? product.getCategory().getId().hashCode() : null,
                    product.getPrice(),
                    product.getUnit().name()))
                    .toList();

            return Result.ok(new ListProductsResponseDto(productDtos, "Products found successfully!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving products"));
        }
    }

    public Result<ProductResponseDto, Exception> showProduct(UUID id) {
        try {
            return productRepository.findById(id).map(product -> {
                // TODO: Create a mapper for this
                ProductDto productDto = new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getCategory() != null ? product.getCategory().getId().hashCode() : null,
                        product.getPrice(),
                        product.getUnit().name());
                return Result.ok(new ProductResponseDto("Product found!", productDto));
            }).orElseGet(() -> Result.error(new Exception("Product not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving product"));
        }
    }
}
