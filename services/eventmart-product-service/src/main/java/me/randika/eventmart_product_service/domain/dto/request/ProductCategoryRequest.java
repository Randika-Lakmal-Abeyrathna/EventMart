package me.randika.eventmart_product_service.domain.dto.request;

import java.util.UUID;

public record ProductCategoryRequest(
        String name,
        String description,
        UUID parentCategoryId
) {
}
