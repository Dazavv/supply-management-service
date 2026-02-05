package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.util.List;

public record DeliveryReportResponse(
        ReportPeriod period,
        List<SupplierReportWithTotals> suppliers,
        GrandTotals grandTotals
) {}
