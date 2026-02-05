package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.math.BigDecimal;

public record ProductReportResponse(
        Long supplierId,
        String productName,
        String productType,
        BigDecimal totalWeight,
        BigDecimal totalPrice
) {}