package me.randika.eventmart_product_service.service.impl;

import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.domain.entity.ProductCategory;
import me.randika.eventmart_product_service.domain.entity.ProductStatus;
import me.randika.eventmart_product_service.exception.DuplicateProductCodeException;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.mapper.ProductMapper;
import me.randika.eventmart_product_service.repository.ProductCategoryRepository;
import me.randika.eventmart_product_service.repository.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testCreateProductSuccess() throws Exception {
        UUID categoryId = UUID.randomUUID();
        ProductRequest request = new ProductRequest("P-001", "Product 1", "Desc", categoryId, BigDecimal.valueOf(9.99), "BrandX", "ACTIVE");

        when(productRepository.existsByProductCode("P-001")).thenReturn(false);
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(mock(ProductCategory.class)));

        Product toSave = Product.builder()
                .productCode("P-001")
                .name("Product 1")
                .description("Desc")
                .price(BigDecimal.valueOf(9.99))
                .categoryId(categoryId)
                .status(ProductStatus.ACTIVE)
                .build();

        Product saved = Product.builder()
                .id(UUID.randomUUID())
                .productCode("P-001")
                .name("Product 1")
                .description("Desc")
                .price(BigDecimal.valueOf(9.99))
                .categoryId(categoryId)
                .status(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductResponse expectedResponse = new ProductResponse(saved.getId(), saved.getProductCode(), saved.getName(), saved.getDescription(), saved.getBrand(), saved.getPrice(), saved.getStatus().name(), saved.getCategoryId(), saved.getCreatedAt(), saved.getUpdatedAt(), List.of());

        when(productMapper.toEntity(request)).thenReturn(toSave);
        when(productRepository.save(any(Product.class))).thenReturn(saved);
        when(productMapper.toResponse(saved)).thenReturn(expectedResponse);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.productCode(), response.productCode());

        verify(productRepository).existsByProductCode("P-001");
        verify(productCategoryRepository).findById(categoryId);
        verify(productRepository).save(any(Product.class));
        verify(productMapper).toResponse(saved);
    }

    @Test
    void testCreateProductDuplicateCode() {
        UUID categoryId = UUID.randomUUID();
        ProductRequest request = new ProductRequest("P-001", "Product 1", "Desc", categoryId, BigDecimal.valueOf(9.99), "BrandX", "ACTIVE");

        when(productRepository.existsByProductCode("P-001")).thenReturn(true);

        assertThrows(DuplicateProductCodeException.class, () -> productService.createProduct(request));

        verify(productRepository).existsByProductCode("P-001");
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void testCreateProductCategoryNotFound() {
        UUID categoryId = UUID.randomUUID();
        ProductRequest request = new ProductRequest("P-002", "Product 2", "Desc2", categoryId, BigDecimal.valueOf(19.99), "BrandY", "ACTIVE");

        when(productRepository.existsByProductCode("P-002")).thenReturn(false);
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ProductCategoryNotFoundException.class, () -> productService.createProduct(request));

        verify(productRepository).existsByProductCode("P-002");
        verify(productCategoryRepository).findById(categoryId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void testGetProductByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = Product.builder()
                .id(id)
                .productCode("P-010")
                .name("Name")
                .price(BigDecimal.ONE)
                .status(ProductStatus.ACTIVE)
                .build();

        ProductResponse expected = new ProductResponse(id, "P-010", "Name", null, null, BigDecimal.ONE, product.getStatus().name(), null, null, null, List.of());

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(expected);

        ProductResponse resp = productService.getProductById(id);

        assertNotNull(resp);
        assertEquals(expected.id(), resp.id());
        verify(productRepository).findById(id);
        verify(productMapper).toResponse(product);
    }

    @Test
    void testGetProductByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
        verify(productRepository).findById(id);
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Product existing = Product.builder()
                .id(id)
                .productCode("OLD-CODE")
                .name("Old")
                .price(BigDecimal.valueOf(5.0))
                .categoryId(categoryId)
                .status(ProductStatus.ACTIVE)
                .build();

        ProductRequest request = new ProductRequest("NEW-CODE", "New Name", "New Desc", categoryId, BigDecimal.valueOf(15.0), "BrandZ", "ACTIVE");

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productRepository.existsByProductCode("NEW-CODE")).thenReturn(false);
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(mock(ProductCategory.class)));

        Product updated = Product.builder()
                .id(id)
                .productCode("NEW-CODE")
                .name("New Name")
                .description("New Desc")
                .price(BigDecimal.valueOf(15.0))
                .categoryId(categoryId)
                .status(ProductStatus.ACTIVE)
                .build();

        ProductResponse expected = new ProductResponse(id, "NEW-CODE", "New Name", "New Desc", null, BigDecimal.valueOf(15.0), updated.getStatus().name(), categoryId, null, null, List.of());

        when(productRepository.save(any(Product.class))).thenReturn(updated);
        when(productMapper.toResponse(updated)).thenReturn(expected);

        ProductResponse resp = productService.updateProduct(id, request);

        assertNotNull(resp);
        assertEquals("NEW-CODE", resp.productCode());
        verify(productRepository).findById(id);
        verify(productRepository).existsByProductCode("NEW-CODE");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProductDuplicateCode() {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Product existing = Product.builder()
                .id(id)
                .productCode("OLD-CODE")
                .status(ProductStatus.ACTIVE)
                .build();

        ProductRequest request = new ProductRequest("OTHER-CODE", "Name", "Desc", categoryId, BigDecimal.ONE, "B", "ACTIVE");

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productRepository.existsByProductCode("OTHER-CODE")).thenReturn(true);

        assertThrows(DuplicateProductCodeException.class, () -> productService.updateProduct(id, request));

        verify(productRepository).findById(id);
        verify(productRepository).existsByProductCode("OTHER-CODE");
    }

    @Test
    void testDeleteProductByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        Product existing = Product.builder().id(id).productCode("P-DEL").status(ProductStatus.ACTIVE).build();

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));

        productService.deleteProductById(id);

        verify(productRepository).findById(id);
        verify(productRepository).delete(existing);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(id));

        verify(productRepository).findById(id);
    }

    @Test
    void testProductList() {
        Product p1 = Product.builder().id(UUID.randomUUID()).productCode("P1").name("One").price(BigDecimal.ONE).status(ProductStatus.ACTIVE).build();
        Product p2 = Product.builder().id(UUID.randomUUID()).productCode("P2").name("Two").price(BigDecimal.TEN).status(ProductStatus.ACTIVE).build();

        Pageable pageable = Pageable.unpaged();
        Page<Product> page = new PageImpl<>(List.of(p1, p2));

        ProductResponse r1 = new ProductResponse(p1.getId(), p1.getProductCode(), p1.getName(), null, null, p1.getPrice(), p1.getStatus().name(), p1.getCategoryId(), null, null, List.of());
        ProductResponse r2 = new ProductResponse(p2.getId(), p2.getProductCode(), p2.getName(), null, null, p2.getPrice(), p2.getStatus().name(), p2.getCategoryId(), null, null, List.of());

        when(productRepository.findAll(pageable)).thenReturn(page);
        when(productMapper.toResponse(p1)).thenReturn(r1);
        when(productMapper.toResponse(p2)).thenReturn(r2);

        Page<ProductResponse> respPage = productService.productList(pageable);

        assertNotNull(respPage);
        assertEquals(2, respPage.getTotalElements());
        assertEquals("P1", respPage.getContent().stream().findFirst().orElseThrow().productCode());
        verify(productRepository).findAll(pageable);
    }
}
