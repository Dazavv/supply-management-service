package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class SupplierAlreadyExistsException extends BusinessException {
    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}
