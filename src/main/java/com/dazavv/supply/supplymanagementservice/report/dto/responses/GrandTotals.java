package com.dazavv.supply.supplymanagementservice.report.dto.responses;

import java.math.BigDecimal;

public record GrandTotals(
        BigDecimal totalWeight,
        BigDecimal totalPrice
) {}
