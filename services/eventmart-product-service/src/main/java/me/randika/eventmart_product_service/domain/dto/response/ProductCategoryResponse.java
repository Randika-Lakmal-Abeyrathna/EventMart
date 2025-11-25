package me.randika.eventmart_product_service.domain.dto.response;

public record ProductCategoryResponse(
        String id,
        String name,
        String description,
        String parentCategoryId
) {
}
