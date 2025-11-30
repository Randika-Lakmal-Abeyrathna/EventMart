package me.randika.eventmart_product_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.exception.DuplicateProductCodeException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductMapper;
import me.randika.eventmart_product_service.repository.ProductCategoryRepository;
import me.randika.eventmart_product_service.repository.ProductRepository;
import me.randika.eventmart_product_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    @Override
        public ProductResponse createProduct(ProductRequest productRequest) throws DuplicateProductCodeException, ProductCategoryNotFoundException {
        if (productRepository.existsByProductCode(productRequest.productCode())){
            throw new DuplicateProductCodeException("Product Code already exists: "+productRequest.productCode());
        }

        productCategoryRepository.findById(productRequest.categoryId())
                .orElseThrow(()-> new ProductCategoryNotFoundException("Product category not found: "+productRequest.categoryId()));


        Product product = productMapper.toEntity(productRequest);

        product.setCategoryId(productRequest.categoryId());

        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(UUID id) throws ProductNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));

        return productMapper.toResponse(product);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) throws ProductNotFoundException, DuplicateProductCodeException, ProductCategoryNotFoundException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product not found: " + id));

        if (!productRequest.productCode().equals(existingProduct.getProductCode())){
            if (productRepository.existsByProductCode(productRequest.productCode())){
                throw new DuplicateProductCodeException("Product Code already exists: "+productRequest.productCode());
            }
        }

        productCategoryRepository.findById(productRequest.categoryId())
                .orElseThrow(()-> new ProductCategoryNotFoundException("Product category not found: "+productRequest.categoryId()));


        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setProductCode(productRequest.productCode());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setCategoryId(productRequest.categoryId());

        existingProduct = productRepository.save(existingProduct);

        return productMapper.toResponse(existingProduct);
    }

    @Transactional
    @Override
    public void deleteProductById(UUID id) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));

        productRepository.delete(existingProduct);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> productList(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }
}
