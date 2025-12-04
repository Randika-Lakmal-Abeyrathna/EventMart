package me.randika.eventmart_product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.exception.DuplicateProductCodeException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a Product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductCategoryNotFoundException, DuplicateProductCodeException {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @Operation(summary = "Update a Product")
    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @RequestBody ProductRequest productRequest)
            throws ProductCategoryNotFoundException, ProductNotFoundException, DuplicateProductCodeException {
        return ResponseEntity.ok(productService.updateProduct(id,productRequest));
    }

    @Operation(summary = "Get paginated list of product")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(productService.productList(pageable));
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id)
            throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Delete product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id)
            throws ProductNotFoundException {

        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }



}
