package com.dazavv.supply.supplymanagementservice.product.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String type,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long supplierId
) {}
