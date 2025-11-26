package me.randika.eventmart_product_service.domain.dto.response;

import java.util.UUID;

public record ProductCategoryResponse(
        UUID id,
        String name,
        String description,
        UUID parentCategoryId
) {
}
