package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductCategoryRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductCategoryResponse;
import me.randika.eventmart_product_service.domain.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryMapperTest {

    private final ProductCategoryMapper mapper = Mappers.getMapper(ProductCategoryMapper.class);

    @Test
    void testRequestToEntity() {
        UUID rootCategory = UUID.randomUUID();
        ProductCategoryRequest req = new ProductCategoryRequest(
                "Electronics",
                "Electronic devices",
                rootCategory
        );

        ProductCategory entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("Electronics", entity.getName());
        assertEquals("Electronic devices", entity.getDescription());
        assertEquals(rootCategory, entity.getParentCategoryId());

        // ignored fields
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    void testEntityToResponse() {
        UUID id = UUID.randomUUID();
        UUID rootCategory = UUID.randomUUID();

        ProductCategory entity = ProductCategory.builder()
                .id(id)
                .name("Home")
                .description("Home appliances")
                .parentCategoryId(rootCategory)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductCategoryResponse res = mapper.toResponse(entity);

        assertNotNull(res);
        assertEquals(id, res.id());
        assertEquals("Home", res.name());
        assertEquals("Home appliances", res.description());
        assertEquals(rootCategory, res.parentCategoryId());
    }
}