package com.dazavv.supply.supplymanagementservice.auth.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class RoleAlreadyExistsException extends BusinessException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
