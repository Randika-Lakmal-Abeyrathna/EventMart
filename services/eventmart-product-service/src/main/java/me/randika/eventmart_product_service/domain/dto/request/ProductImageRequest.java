package me.randika.eventmart_product_service.domain.dto.request;

import java.util.UUID;

public record ProductImageRequest(
        String imageUrl,
        UUID productId,
        boolean isPrimary
) {
}
