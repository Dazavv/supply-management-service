package com.dazavv.supply.supplymanagementservice.auth.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
