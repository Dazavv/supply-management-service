package com.dazavv.supply.supplymanagementservice.delivery.dto.responses;

import java.math.BigDecimal;

public record DeliveryItemResponse(
        Long productId,
        String productName,
        BigDecimal weight,
        BigDecimal price
) {}