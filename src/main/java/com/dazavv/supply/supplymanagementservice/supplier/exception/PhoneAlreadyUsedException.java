package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class PhoneAlreadyUsedException extends BusinessException {
    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
