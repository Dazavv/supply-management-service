package com.dazavv.supply.supplymanagementservice.delivery.dto.responses;

import com.dazavv.supply.supplymanagementservice.delivery.utils.DeliveryStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DeliveryResponse(
        Long id,
        LocalDateTime deliveryDateTime,
        Long supplierId,
        DeliveryStatus status,
        BigDecimal totalAmount,
        String deliveryAddress,
        String comment,
        List<DeliveryItemResponse> items
) {}