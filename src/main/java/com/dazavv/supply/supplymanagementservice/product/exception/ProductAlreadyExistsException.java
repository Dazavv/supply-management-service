package com.dazavv.supply.supplymanagementservice.product.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
