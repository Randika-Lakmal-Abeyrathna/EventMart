package me.randika.eventmart_product_service.domain.dto.response;

public record ProductImageResponse(
        String id,
        String productId,
        String imageUrl,
        boolean isPrimary
) {
}
