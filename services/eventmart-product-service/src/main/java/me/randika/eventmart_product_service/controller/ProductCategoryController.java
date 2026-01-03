package me.randika.eventmart_product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import me.randika.eventmart_product_service.domain.dto.request.ProductCategoryRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductCategoryResponse;
import me.randika.eventmart_product_service.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/productcategory")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Operation(summary = "create a product category")
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> createProductCategory(@RequestBody ProductCategoryRequest productCategoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(productCategoryService.createProductCategory(productCategoryRequest));
    }

    @Operation(summary = "update a product category")
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> updateProductCategory(@PathVariable UUID id, @RequestBody ProductCategoryRequest productCategoryRequest){
        return ResponseEntity.ok(productCategoryService.updateProductCategory(id,productCategoryRequest));
    }

    @Operation(summary = "Get all product categories")
    @GetMapping
    public ResponseEntity<Page<ProductCategoryResponse>> getAllProductCategories(Pageable pageable){
        return ResponseEntity.ok(productCategoryService.getProductCategories(pageable));
    }

    @Operation(summary = "Get product category by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getProductCategoryById(@PathVariable UUID id){
        return ResponseEntity.ok(productCategoryService.getProductCategoryById(id));
    }

    @Operation(summary = "Delete product category by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategoryById(@PathVariable UUID id){
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }



}
