package com.dazavv.supply.supplymanagementservice.delivery.exception;

import com.dazavv.supply.supplymanagementservice.common.exception.BusinessException;

public class DeliveryNotFoundException extends BusinessException {
    public DeliveryNotFoundException(String message) {
        super(message);
    }
}
