package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class InvalidSupplierDataException extends BusinessException {
    public InvalidSupplierDataException(String message) {
        super(message);
    }
}
