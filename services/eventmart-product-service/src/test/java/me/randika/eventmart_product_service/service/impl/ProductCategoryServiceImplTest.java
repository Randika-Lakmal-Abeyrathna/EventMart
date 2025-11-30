package me.randika.eventmart_product_service.service.impl;

import me.randika.eventmart_product_service.domain.dto.request.ProductCategoryRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductCategoryResponse;
import me.randika.eventmart_product_service.domain.entity.ProductCategory;
import me.randika.eventmart_product_service.exception.DuplicateProductCategoryException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductCategoryMapper;
import me.randika.eventmart_product_service.repository.ProductCategoryRepository;
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

class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductCategoryMapper productCategoryMapper;

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    private UUID catId;
    private UUID parentId;
    private ProductCategory entity;
    private ProductCategoryRequest request;
    private ProductCategoryResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        catId = UUID.randomUUID();
        parentId = UUID.randomUUID();

        request = new ProductCategoryRequest(
                "Electronics",
                "Electronic devices",
                parentId
        );

        entity = ProductCategory.builder()
                .id(catId)
                .name("Electronics")
                .description("Electronic devices")
                .parentCategoryId(parentId)
                .build();

        response = new ProductCategoryResponse(
                catId,
                "Electronics",
                "Electronic devices",
                parentId
        );
    }

    @Test
    void testCreateProductCategory_Success() throws DuplicateProductCategoryException, ProductCategoryNotFoundException {
        when(productCategoryRepository.findByName("Electronics"))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.findById(parentId))
                .thenReturn(Optional.of(new ProductCategory()));

        when(productCategoryMapper.toEntity(request)).thenReturn(entity);
        when(productCategoryRepository.save(entity)).thenReturn(entity);
        when(productCategoryMapper.toResponse(entity)).thenReturn(response);

        ProductCategoryResponse result = productCategoryService.createProductCategory(request);

        assertNotNull(result);
        assertEquals("Electronics", result.name());

        verify(productCategoryRepository).save(entity);
    }

    @Test
    void testCreateProductCategory_Fails_DuplicateName() {
        when(productCategoryRepository.findByName("Electronics"))
                .thenReturn(Optional.of(entity));

        assertThrows(DuplicateProductCategoryException.class,
                () -> productCategoryService.createProductCategory(request));
    }

    @Test
    void testCreateProductCategory_Fails_ParentNotFound() {
        when(productCategoryRepository.findByName("Electronics"))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.findById(parentId))
                .thenReturn(Optional.empty());

        assertThrows(ProductCategoryNotFoundException.class,
                () -> productCategoryService.createProductCategory(request));
    }

    @Test
    void testGetProductCategoryById_Success() throws ProductCategoryNotFoundException {
        when(productCategoryRepository.findById(catId)).thenReturn(Optional.of(entity));
        when(productCategoryMapper.toResponse(entity)).thenReturn(response);

        ProductCategoryResponse result = productCategoryService.getProductCategoryById(catId);

        assertEquals("Electronics", result.name());
        verify(productCategoryRepository).findById(catId);
    }

    @Test
    void testGetProductCategoryById_NotFound() {
        when(productCategoryRepository.findById(catId)).thenReturn(Optional.empty());

        assertThrows(ProductCategoryNotFoundException.class,
                () -> productCategoryService.getProductCategoryById(catId));
    }


    @Test
    void testUpdateProductCategory_Success() throws DuplicateProductCategoryException, ProductCategoryNotFoundException {
        ProductCategory existing = ProductCategory.builder()
                .id(catId)
                .name("OldName")
                .description("Old")
                .parentCategoryId(parentId)
                .build();

        when(productCategoryRepository.findById(catId))
                .thenReturn(Optional.of(existing));

        when(productCategoryRepository.findByName("Electronics"))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.findById(parentId))
                .thenReturn(Optional.of(new ProductCategory()));

        when(productCategoryRepository.save(existing)).thenReturn(existing);
        when(productCategoryMapper.toResponse(existing)).thenReturn(response);

        ProductCategoryResponse result = productCategoryService.updateProductCategory(catId, request);

        assertEquals("Electronics", result.name());
    }

    @Test
    void testUpdateProductCategory_Fails_NotFound() {
        when(productCategoryRepository.findById(catId))
                .thenReturn(Optional.empty());

        assertThrows(ProductCategoryNotFoundException.class,
                () -> productCategoryService.updateProductCategory(catId, request));
    }

    @Test
    void testUpdateProductCategory_Fails_DuplicateName() {
        ProductCategory existing = ProductCategory.builder()
                .id(catId)
                .name("AnotherName")
                .build();

        when(productCategoryRepository.findById(catId)).thenReturn(Optional.of(existing));

        when(productCategoryRepository.findByName("Electronics"))
                .thenReturn(Optional.of(entity));

        assertThrows(DuplicateProductCategoryException.class,
                () -> productCategoryService.updateProductCategory(catId, request));
    }

    @Test
    void testDeleteProductCategory_Success() throws ProductCategoryNotFoundException {
        when(productCategoryRepository.findById(catId)).thenReturn(Optional.of(entity));

        productCategoryService.deleteProductCategory(catId);

        verify(productCategoryRepository).delete(entity);
    }

    @Test
    void testDeleteProductCategory_NotFound() {
        when(productCategoryRepository.findById(catId)).thenReturn(Optional.empty());

        assertThrows(ProductCategoryNotFoundException.class,
                () -> productCategoryService.deleteProductCategory(catId));
    }

    @Test
    void testGetProductCategories() {
        Pageable pageable = mock(Pageable.class);
        Page<ProductCategory> page = new PageImpl<>(List.of(entity));

        when(productCategoryRepository.findAll(pageable)).thenReturn(page);
        when(productCategoryMapper.toResponse(entity)).thenReturn(response);

        Page<ProductCategoryResponse> result = productCategoryService.getProductCategories(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Electronics", result.getContent().get(0).name());
    }
}
