package com.dazavv.supply.supplymanagementservice.product.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest (
        @NotBlank
        @Size(max = 20)
        String name,

        @NotBlank
        @Size(max = 30)
        String type,

        @NotNull
        @Positive
        BigDecimal price
) {}
