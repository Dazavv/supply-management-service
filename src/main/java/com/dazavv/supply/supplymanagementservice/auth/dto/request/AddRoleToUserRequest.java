package com.dazavv.supply.supplymanagementservice.auth.dto.request;

import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import jakarta.validation.constraints.NotNull;

public record AddRoleToUserRequest (
        @NotNull
        Long id,

        @NotNull
        Role role
) {}
