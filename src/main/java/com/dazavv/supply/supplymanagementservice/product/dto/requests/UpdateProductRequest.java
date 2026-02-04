package com.dazavv.supply.supplymanagementservice.product.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest (
        @NotNull
        Long productId,
        @Size(max = 20)
        String name,

        @Size(max = 30)
        String type,

        @Positive
        BigDecimal price
) {}
