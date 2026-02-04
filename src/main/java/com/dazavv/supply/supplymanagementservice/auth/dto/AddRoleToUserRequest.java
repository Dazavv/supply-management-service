package com.dazavv.supply.supplymanagementservice.auth.dto;

import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRoleToUserRequest {
    @NotBlank
    private String login;

    @NotBlank
    private Role role;
}
