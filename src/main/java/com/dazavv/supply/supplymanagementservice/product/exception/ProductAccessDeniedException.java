package com.dazavv.supply.supplymanagementservice.product.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class ProductAccessDeniedException extends BusinessException {
    public ProductAccessDeniedException(String message) {
        super(message);
    }
}
