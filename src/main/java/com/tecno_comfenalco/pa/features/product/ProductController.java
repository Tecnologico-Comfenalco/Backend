package com.tecno_comfenalco.pa.features.product;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.product.dto.request.EditProductRequestDto;
import com.tecno_comfenalco.pa.features.product.dto.request.RegisterProductRequestDto;
import com.tecno_comfenalco.pa.features.product.dto.response.DisableProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.EditProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.ListProductsResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.ProductResponseDto;
import com.tecno_comfenalco.pa.features.product.dto.response.RegisterProductResponseDto;
import com.tecno_comfenalco.pa.features.product.service.ProductServices;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;

    @PostMapping("/save")
    public ResponseEntity<RegisterProductResponseDto> saveProduct(
            @RequestBody @Valid RegisterProductRequestDto dtoProduct) {
        Result<RegisterProductResponseDto, Exception> result = productServices.saveProducts(dtoProduct);

        return ResponseEntityHelper.toResponseEntity(result,
                new RegisterProductResponseDto("Product created succesfull!"),
                new RegisterProductResponseDto("Error for register product!"));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<EditProductResponseDto> editProduct(@PathVariable UUID id,
            @RequestBody @Valid EditProductRequestDto dtoProduct) {
        Result<EditProductResponseDto, Exception> result = productServices.editProduct(id, dtoProduct);

        return ResponseEntityHelper.toResponseEntity(result,
                new EditProductResponseDto("Product modified succesfull", id),
                new EditProductResponseDto("Error to modified product!", id));
    }

    @DeleteMapping("/disabled/{id}")
    public ResponseEntity<DisableProductResponseDto> disabledProduct(@PathVariable UUID id) {

        Result<DisableProductResponseDto, Exception> result = productServices.disabledProduct(id);

        return ResponseEntityHelper.toResponseEntity(result,
                new DisableProductResponseDto("Product modified succesfull"),
                new DisableProductResponseDto("Error to modified product!"));
    }

    @GetMapping("/all")
    public ResponseEntity<ListProductsResponseDto> listProducts() {
        Result<ListProductsResponseDto, Exception> result = productServices.listProducts();
        return ResponseEntity.ok().body(result.getValue());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponseDto> showProduct(@PathVariable UUID id) {
        Result<ProductResponseDto, Exception> result = productServices.showProduct(id);

        return ResponseEntityHelper.toResponseEntity(result,
                new ProductResponseDto(null, result.getValue().productEntity()),
                new ProductResponseDto("product not found!", null));
    }
}
