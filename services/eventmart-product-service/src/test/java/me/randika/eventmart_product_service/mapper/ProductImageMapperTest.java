package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductImageRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductImageResponse;
import me.randika.eventmart_product_service.domain.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductImageMapperTest {
    private final ProductImageMapper mapper = Mappers.getMapper(ProductImageMapper.class);

    @Test
    void testRequestToEntity() {
        UUID productId = UUID.randomUUID();
        ProductImageRequest req = new ProductImageRequest(
                "http://image.com/1.png",
                productId,
                true
        );

        ProductImage entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("http://image.com/1.png", entity.getImageUrl());
        assertEquals(productId, entity.getProductId());
        assertTrue( entity.getIsPrimary());

    }

    @Test
    void testEntityToResponse() {
        UUID id = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        ProductImage entity = ProductImage.builder()
                .id(id)
                .productId(productId)
                .imageUrl("http://image.com/1.png")
                .isPrimary(true)
                .createdAt(LocalDateTime.now())
                .build();

        ProductImageResponse res = mapper.toResponse(entity);

        assertNotNull(res);
        assertEquals(id, res.id());
        assertEquals("http://image.com/1.png", res.imageUrl());
        assertEquals(productId, res.productId());
        assertTrue(res.isPrimary());
    }
}