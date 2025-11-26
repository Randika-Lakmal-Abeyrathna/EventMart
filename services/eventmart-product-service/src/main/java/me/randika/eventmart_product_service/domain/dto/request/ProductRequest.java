package me.randika.eventmart_product_service.domain.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        String name,
        String description,
        UUID categoryId,
        BigDecimal price,
        String brand,
        String status
) { }
