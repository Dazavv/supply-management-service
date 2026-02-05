package com.dazavv.supply.supplymanagementservice.auth.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
