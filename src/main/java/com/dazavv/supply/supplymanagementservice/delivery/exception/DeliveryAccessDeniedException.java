package com.dazavv.supply.supplymanagementservice.delivery.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class DeliveryAccessDeniedException extends BusinessException {
    public DeliveryAccessDeniedException(String message) {
        super(message);
    }
}
