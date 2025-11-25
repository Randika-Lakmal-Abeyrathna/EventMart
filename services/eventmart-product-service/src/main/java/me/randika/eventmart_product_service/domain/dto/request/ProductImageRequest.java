package me.randika.eventmart_product_service.domain.dto.request;

public record ProductImageRequest(
        String imageUrl,
        boolean isPrimary
) {
}
