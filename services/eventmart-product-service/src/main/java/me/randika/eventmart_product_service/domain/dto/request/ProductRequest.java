package me.randika.eventmart_product_service.domain.dto.request;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        String categoryId,
        BigDecimal price,
        String brand,
        String status
) { }
