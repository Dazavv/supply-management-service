package com.dazavv.supply.supplymanagementservice.supplier.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSupplierRequest (
        @NotBlank
        Long userId,

        @NotBlank
        @Size(max = 30)
        String companyName,

        @NotBlank
        @Size(max = 20)
        String code,

        @Size(max = 12)
        String phoneNumber,

        @Size(max = 30)
        String email
) {}
