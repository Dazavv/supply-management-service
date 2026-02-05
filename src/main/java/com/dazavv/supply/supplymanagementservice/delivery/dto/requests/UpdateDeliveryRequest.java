package com.dazavv.supply.supplymanagementservice.delivery.dto.requests;

import com.dazavv.supply.supplymanagementservice.delivery.utils.DeliveryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateDeliveryRequest(
        @FutureOrPresent
        LocalDateTime deliveryDateTime,

        List<DeliveryItemRequest> items,

        DeliveryStatus status,

        @Size(max = 255)
        String deliveryAddress,

        @Size(max = 255)
        String comment

) {}
