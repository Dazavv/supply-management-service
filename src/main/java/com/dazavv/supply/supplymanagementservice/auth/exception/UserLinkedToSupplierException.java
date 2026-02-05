package com.dazavv.supply.supplymanagementservice.auth.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class UserLinkedToSupplierException extends BusinessException {
    public UserLinkedToSupplierException(String message) {
        super(message);
    }
}
