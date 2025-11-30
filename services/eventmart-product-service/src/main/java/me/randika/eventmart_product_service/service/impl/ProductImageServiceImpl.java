package me.randika.eventmart_product_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.randika.eventmart_product_service.domain.dto.request.ProductImageRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductImageResponse;
import me.randika.eventmart_product_service.domain.entity.ProductImage;
import me.randika.eventmart_product_service.exception.ProductImageNotFound;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductImageMapper;
import me.randika.eventmart_product_service.repository.ProductImageRepository;
import me.randika.eventmart_product_service.repository.ProductRepository;
import me.randika.eventmart_product_service.service.ProductImageService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductImageResponse createProductImage(ProductImageRequest productImageRequest) throws ProductNotFoundException {

        productRepository.findById(productImageRequest.productId())
                .orElseThrow(()-> new ProductNotFoundException("Product not found: "+productImageRequest.productId()));

        ProductImage productImage = productImageMapper.toEntity(productImageRequest);
        productImage = productImageRepository.save(productImage);
        return productImageMapper.toResponse(productImage);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductImageResponse getProductImage(UUID id) throws ProductImageNotFound {

        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ProductImageNotFound("Product image not found: " + id));

        return productImageMapper.toResponse(productImage);
    }

    @Transactional
    @Override
    public void deleteProductImage(UUID id) throws ProductImageNotFound {
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ProductImageNotFound("Product image not found: " + id));
        productImageRepository.delete(productImage);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductImageResponse> getProductImages(Pageable pageable) {
        return productImageRepository.findAll(pageable).map(productImageMapper::toResponse);
    }
}
