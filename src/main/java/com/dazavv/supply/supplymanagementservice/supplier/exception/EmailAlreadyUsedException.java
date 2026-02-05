package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class EmailAlreadyUsedException extends BusinessException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
