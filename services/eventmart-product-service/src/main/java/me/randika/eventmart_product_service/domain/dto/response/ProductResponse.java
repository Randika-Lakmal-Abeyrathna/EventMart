package me.randika.eventmart_product_service.domain.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        String brand,
        BigDecimal price,
        String status,
        UUID categoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ProductImageResponse> images
) {
}
