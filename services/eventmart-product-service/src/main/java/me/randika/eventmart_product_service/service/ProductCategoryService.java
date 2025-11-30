package me.randika.eventmart_product_service.service;

import me.randika.eventmart_product_service.domain.dto.request.ProductCategoryRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductCategoryResponse;
import me.randika.eventmart_product_service.exception.DuplicateProductCategoryException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface ProductCategoryService {

    ProductCategoryResponse createProductCategory(ProductCategoryRequest productCategoryRequest) throws DuplicateProductCategoryException, ProductCategoryNotFoundException;

    ProductCategoryResponse getProductCategoryById(UUID productCategoryId) throws ProductCategoryNotFoundException;

    ProductCategoryResponse updateProductCategory(UUID productCategoryId, ProductCategoryRequest productCategoryRequest) throws ProductCategoryNotFoundException, DuplicateProductCategoryException;

    void deleteProductCategory(UUID productCategoryId) throws ProductCategoryNotFoundException;

    Page<ProductCategoryResponse> getProductCategories(Pageable pageable);

}
