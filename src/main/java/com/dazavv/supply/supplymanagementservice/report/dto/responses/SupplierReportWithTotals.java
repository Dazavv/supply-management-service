package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.math.BigDecimal;
import java.util.List;

public record SupplierReportWithTotals(
        Long supplierId,
        String supplierName,
        BigDecimal totalWeight,
        BigDecimal totalPrice,
        List<ProductReportResponse> products
) {}
