package com.dazavv.supply.supplymanagementservice.auth.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class AuthException extends BusinessException {

    public AuthException(String msg) {
        super(msg);
    }
}
