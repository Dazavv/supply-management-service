package com.dazavv.supply.supplymanagementservice.delivery.dto.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record CreateDeliveryRequest (
        @NotNull
        @FutureOrPresent
        LocalDateTime deliveryDateTime,
        @NotNull
        List<DeliveryItemRequest> items,
        @NotBlank
        @Size(max = 255)
        String deliveryAddress,
        @Size(max = 255)
        String comment
) {}
