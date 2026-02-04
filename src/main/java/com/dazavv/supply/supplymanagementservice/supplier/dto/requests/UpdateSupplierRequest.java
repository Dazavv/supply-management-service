package com.dazavv.supply.supplymanagementservice.supplier.dto.requests;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateSupplierRequest (
        @NotNull Long supplierId,

        @Size(max = 30)
        String companyName,

        @Size(max = 12)
        String phoneNumber,

        @Size(max = 30)
        String email
) {}
