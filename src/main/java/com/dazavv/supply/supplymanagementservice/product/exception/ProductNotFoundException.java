package com.dazavv.supply.supplymanagementservice.product.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
