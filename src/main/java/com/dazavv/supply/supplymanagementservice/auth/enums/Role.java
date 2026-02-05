package com.dazavv.supply.supplymanagementservice.auth.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, SUPPLIER, VIEWER;

    @Override
    public String getAuthority() {
        return name();
    }
}
