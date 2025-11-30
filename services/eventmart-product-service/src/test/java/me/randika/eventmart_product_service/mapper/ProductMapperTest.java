package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.domain.entity.ProductStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {


    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);


    @Test
    void testProductRequestToEntity() {
        UUID categoryId = UUID.randomUUID();
        ProductRequest req = new ProductRequest(
                "sku-001",
                "Laptop",
                "Gaming laptop",
                categoryId,
                new BigDecimal("4999.99"),
                "ASUS",
                "ACTIVE"
        );

        Product entity = productMapper.toEntity(req);

        assertEquals("sku-001", entity.getProductCode());
        assertEquals("Laptop", entity.getName());
        assertEquals("Gaming laptop", entity.getDescription());
        assertEquals(categoryId, entity.getCategoryId());
        assertEquals(new BigDecimal("4999.99"), entity.getPrice());
        assertEquals(ProductStatus.ACTIVE, entity.getStatus());

        // ignored fields should be NULL
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    void testProductEntityToResponse() {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        Product product = Product.builder()
                .id(id)
                .productCode("sku-001")
                .name("Phone")
                .description("Nice phone")
                .price(new BigDecimal("900"))
                .brand("Samsung")
                .status(ProductStatus.ACTIVE)
                .categoryId(categoryId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductResponse dto = productMapper.toResponse(product);

        assertEquals("sku-001", dto.productCode());
        assertEquals("Phone", dto.name());
        assertEquals("Samsung", dto.brand());
        assertEquals(categoryId, dto.categoryId());
        assertEquals(new BigDecimal("900"), dto.price());
        assertEquals("ACTIVE", dto.status());
        assertEquals(id, dto.id());
        assertEquals(product.getCreatedAt(), dto.createdAt());
        assertEquals(product.getUpdatedAt(), dto.updatedAt());
    }

}