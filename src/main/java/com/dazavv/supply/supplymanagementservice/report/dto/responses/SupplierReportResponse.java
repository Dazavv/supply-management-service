package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.util.List;

public record SupplierReportResponse(
        Long supplierId,
        String supplierName,
        List<ProductReportResponse> products
) {}