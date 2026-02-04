package com.dazavv.supply.supplymanagementservice.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    @NotBlank
    private String refreshToken;
}
