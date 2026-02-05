package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.time.LocalDateTime;

public record ReportPeriod(
        LocalDateTime start,
        LocalDateTime end
) {}
