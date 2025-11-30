package me.randika.eventmart_product_service.service.impl;

import me.randika.eventmart_product_service.domain.dto.request.ProductImageRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductImageResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.domain.entity.ProductImage;
import me.randika.eventmart_product_service.exception.ProductImageNotFound;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductImageMapper;
import me.randika.eventmart_product_service.repository.ProductImageRepository;
import me.randika.eventmart_product_service.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductImageServiceImplTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ProductImageMapper productImageMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductImageServiceImpl productImageService;

    private UUID imageId;
    private UUID productId;

    private ProductImageRequest request;
    private ProductImage entity;
    private ProductImageResponse response;
    private Product  product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        imageId = UUID.randomUUID();
        productId = UUID.randomUUID();

        product = Product.builder()
                .id(productId)
                .name("product")
                .description("description")
                .productCode("sku-001")
                .brand("brand")
                .build();

        request = new ProductImageRequest(

                "https://image-url.com/sample.jpg",
                productId,
                true
        );

        entity = ProductImage.builder()
                .id(imageId)
                .productId(productId)
                .imageUrl("https://image-url.com/sample.jpg")
                .isPrimary(true)
                .build();

        response = new ProductImageResponse(
                imageId,
                productId,
                "https://image-url.com/sample.jpg",
                true
        );
    }

    // ---------------------------------------------------------------------------
    //  CREATE PRODUCT IMAGE
    // ---------------------------------------------------------------------------

    @Test
    void testCreateProductImage_Success() throws ProductNotFoundException {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productImageMapper.toEntity(request)).thenReturn(entity);
        when(productImageRepository.save(entity)).thenReturn(entity);
        when(productImageMapper.toResponse(entity)).thenReturn(response);

        ProductImageResponse result = productImageService.createProductImage(request);

        assertNotNull(result);
        assertEquals(imageId, result.id());
        verify(productImageRepository).save(entity);
    }

    @Test
    void testCreateProductImage_Fails_ProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productImageService.createProductImage(request));
    }

    // ---------------------------------------------------------------------------
    //  GET PRODUCT IMAGE
    // ---------------------------------------------------------------------------

    @Test
    void testGetProductImage_Success() throws ProductImageNotFound {
        when(productImageRepository.findById(imageId)).thenReturn(Optional.of(entity));
        when(productImageMapper.toResponse(entity)).thenReturn(response);

        ProductImageResponse result = productImageService.getProductImage(imageId);

        assertEquals(imageId, result.id());
        verify(productImageRepository).findById(imageId);
    }

    @Test
    void testGetProductImage_NotFound() {
        when(productImageRepository.findById(imageId)).thenReturn(Optional.empty());

        assertThrows(ProductImageNotFound.class,
                () -> productImageService.getProductImage(imageId));
    }

    // ---------------------------------------------------------------------------
    //  DELETE PRODUCT IMAGE
    // ---------------------------------------------------------------------------

    @Test
    void testDeleteProductImage_Success() throws ProductImageNotFound {
        when(productImageRepository.findById(imageId)).thenReturn(Optional.of(entity));

        productImageService.deleteProductImage(imageId);

        verify(productImageRepository).delete(entity);
    }

    @Test
    void testDeleteProductImage_NotFound() {
        when(productImageRepository.findById(imageId)).thenReturn(Optional.empty());

        assertThrows(ProductImageNotFound.class,
                () -> productImageService.deleteProductImage(imageId));
    }

    // ---------------------------------------------------------------------------
    //  GET PAGINATED IMAGES
    // ---------------------------------------------------------------------------

    @Test
    void testGetProductImages() {
        Pageable pageable = mock(Pageable.class);
        Page<ProductImage> page = new PageImpl<>(List.of(entity));

        when(productImageRepository.findAll(pageable)).thenReturn(page);
        when(productImageMapper.toResponse(entity)).thenReturn(response);

        Page<ProductImageResponse> result = productImageService.getProductImages(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(imageId, result.getContent().get(0).id());
    }
}
