package com.dazavv.supply.supplymanagementservice.report.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReportRequest(
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
) {}