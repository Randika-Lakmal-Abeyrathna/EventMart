package me.randika.eventmart_product_service.service;

import me.randika.eventmart_product_service.domain.dto.request.ProductImageRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductImageResponse;
import me.randika.eventmart_product_service.exception.ProductImageNotFound;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductImageService {

    ProductImageResponse createProductImage(ProductImageRequest productImageRequest) throws ProductNotFoundException;

    ProductImageResponse getProductImage(UUID id) throws ProductImageNotFound;

    void deleteProductImage(UUID id) throws ProductImageNotFound;

    Page<ProductImageResponse> getProductImages(Pageable pageable);
}
