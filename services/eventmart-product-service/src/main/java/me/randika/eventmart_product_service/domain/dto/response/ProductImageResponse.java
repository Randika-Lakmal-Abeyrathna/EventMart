package me.randika.eventmart_product_service.domain.dto.response;

import java.util.UUID;

public record ProductImageResponse(
        UUID id,
        UUID productId,
        String imageUrl,
        boolean isPrimary
) {
}
