package com.dazavv.supply.supplymanagementservice.supplier.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class SupplierNotFoundException extends BusinessException {
    public SupplierNotFoundException(String s) {
        super(s);
    }
}
