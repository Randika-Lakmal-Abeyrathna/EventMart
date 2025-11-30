package me.randika.eventmart_product_service.service;

import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.exception.DuplicateProductCodeException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ProductService {

        ProductResponse createProduct(ProductRequest productRequest) throws DuplicateProductCodeException, ProductCategoryNotFoundException;

    ProductResponse getProductById(UUID id) throws ProductNotFoundException;

    ProductResponse updateProduct(UUID id, ProductRequest productRequest) throws ProductNotFoundException, DuplicateProductCodeException, ProductCategoryNotFoundException;

    void deleteProductById(UUID id) throws ProductNotFoundException;

    Page<ProductResponse> productList(Pageable pageable);

}
