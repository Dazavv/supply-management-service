package com.dazavv.supply.supplymanagementservice.delivery.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DeliveryItemRequest(
        @NotNull
        Long productId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal weight,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal price
) {}
