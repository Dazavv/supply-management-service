package com.dazavv.supply.supplymanagementservice.supplier.dto.responses;

import java.time.LocalDateTime;

public record SupplierResponse (
        Long id,
        String code,
        String companyName,
        String phoneNumber,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
