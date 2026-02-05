package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class AdminConflictException extends BusinessException {
    public AdminConflictException(String message) {
        super(message);
    }
}
