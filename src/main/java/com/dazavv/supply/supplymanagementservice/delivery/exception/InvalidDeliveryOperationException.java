package com.dazavv.supply.supplymanagementservice.delivery.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class InvalidDeliveryOperationException extends BusinessException {
    public InvalidDeliveryOperationException(String message) {
        super(message);
    }
}
