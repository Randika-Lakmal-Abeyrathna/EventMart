package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.domain.entity.ProductStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {


    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);


    @Test
    void testProductRequestToEntity() {
        ProductRequest req = new ProductRequest(
                "Laptop",
                "Gaming laptop",
                "cat-123",
                new BigDecimal("4999.99"),
                "ASUS",
                "ACTIVE"
        );

        Product entity = productMapper.toEntity(req);

        assertEquals("Laptop", entity.getName());
        assertEquals("Gaming laptop", entity.getDescription());
        assertEquals("cat-123", entity.getCategoryId());
        assertEquals(new BigDecimal("4999.99"), entity.getPrice());
        assertEquals(ProductStatus.ACTIVE, entity.getStatus());

        // ignored fields should be NULL
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    void testProductEntityToResponse() {
        Product product = Product.builder()
                .id("p-1")
                .name("Phone")
                .description("Nice phone")
                .price(new BigDecimal("900"))
                .brand("Samsung")
                .status(ProductStatus.ACTIVE)
                .categoryId("cat-200")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductResponse dto = productMapper.toResponse(product);

        assertEquals("Phone", dto.name());
        assertEquals("Samsung", dto.brand());
        assertEquals("cat-200", dto.categoryId());
        assertEquals(new BigDecimal("900"), dto.price());
        assertEquals("ACTIVE", dto.status());
        assertEquals("p-1", dto.id());
        assertEquals(product.getCreatedAt(), dto.createdAt());
        assertEquals(product.getUpdatedAt(), dto.updatedAt());
    }

}