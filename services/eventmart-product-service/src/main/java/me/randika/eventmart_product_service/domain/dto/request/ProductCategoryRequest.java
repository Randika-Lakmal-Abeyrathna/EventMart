package me.randika.eventmart_product_service.domain.dto.request;

public record ProductCategoryRequest(
        String name,
        String description,
        String parentCategoryId
) {
}
