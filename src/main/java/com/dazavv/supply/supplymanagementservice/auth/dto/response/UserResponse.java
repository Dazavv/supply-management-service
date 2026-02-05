package com.dazavv.supply.supplymanagementservice.auth.dto.response;

import com.dazavv.supply.supplymanagementservice.auth.enums.Role;

import java.util.Set;

public record UserResponse(
        Long id,
        String login,
        String name,
        String surname,
        String email,
        String phoneNumber,
        Set<Role> roles
) {}
