package me.randika.eventmart_product_service.domain.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        String id,
        String name,
        String description,
        String brand,
        BigDecimal price,
        String status,
        String categoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ProductImageResponse> images
) {
}
