package me.randika.eventmart_product_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.randika.eventmart_product_service.domain.dto.request.ProductCategoryRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductCategoryResponse;
import me.randika.eventmart_product_service.domain.entity.ProductCategory;
import me.randika.eventmart_product_service.exception.DuplicateProductCategoryException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductCategoryMapper;
import me.randika.eventmart_product_service.repository.ProductCategoryRepository;
import me.randika.eventmart_product_service.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Transactional
    @Override
    public ProductCategoryResponse createProductCategory(ProductCategoryRequest productCategoryRequest) throws DuplicateProductCategoryException, ProductCategoryNotFoundException {

        if (productCategoryRepository.findByName(productCategoryRequest.name()).isPresent()) {
            throw new DuplicateProductCategoryException("Product category name exists: "+ productCategoryRequest.name());
        }

        productCategoryRepository.findById(productCategoryRequest.parentCategoryId())
                .orElseThrow(()-> new ProductCategoryNotFoundException("parent product category not found: "+
                        productCategoryRequest.parentCategoryId()));

        ProductCategory  productCategory = productCategoryMapper.toEntity(productCategoryRequest);

        productCategory = productCategoryRepository.save(productCategory);

        return productCategoryMapper.toResponse(productCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductCategoryResponse getProductCategoryById(UUID productCategoryId) throws ProductCategoryNotFoundException {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category not found: " + productCategoryId));
        return productCategoryMapper.toResponse(productCategory);
    }

    @Transactional
    @Override
    public ProductCategoryResponse updateProductCategory(UUID productCategoryId, ProductCategoryRequest productCategoryRequest) throws ProductCategoryNotFoundException, DuplicateProductCategoryException {
        ProductCategory existingProductCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category not found: " + productCategoryId));

        if (!existingProductCategory.getName().equals(productCategoryRequest.name())) {
            if(productCategoryRepository.findByName(productCategoryRequest.name()).isPresent()) {
                throw new DuplicateProductCategoryException("Product category already exists: "+productCategoryRequest.name());
            }
        }

        productCategoryRepository.findById(productCategoryRequest.parentCategoryId())
                .orElseThrow(()-> new ProductCategoryNotFoundException("parent product category not found: "+
                        productCategoryRequest.parentCategoryId()));

        existingProductCategory.setName(productCategoryRequest.name());
        existingProductCategory.setDescription(productCategoryRequest.description());
        existingProductCategory.setParentCategoryId(productCategoryRequest.parentCategoryId());

        existingProductCategory = productCategoryRepository.save(existingProductCategory);

        return productCategoryMapper.toResponse(existingProductCategory);
    }

    @Transactional
    @Override
    public void deleteProductCategory(UUID productCategoryId) throws ProductCategoryNotFoundException {
        ProductCategory existingProductCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category not found: " + productCategoryId));

        productCategoryRepository.delete(existingProductCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductCategoryResponse> getProductCategories(Pageable pageable) {
        return productCategoryRepository
                .findAll(pageable)
                .map(productCategoryMapper::toResponse);
    }
}
