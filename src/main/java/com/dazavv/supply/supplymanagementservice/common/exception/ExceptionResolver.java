package com.dazavv.supply.supplymanagementservice.common.exception;

import com.dazavv.supply.supplymanagementservice.auth.exception.*;

import com.dazavv.supply.supplymanagementservice.delivery.exception.DeliveryAccessDeniedException;
import com.dazavv.supply.supplymanagementservice.delivery.exception.DeliveryNotFoundException;

import com.dazavv.supply.supplymanagementservice.product.exception.ProductAccessDeniedException;
import com.dazavv.supply.supplymanagementservice.product.exception.ProductAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.product.exception.ProductNotFoundException;

import com.dazavv.supply.supplymanagementservice.supplier.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessage> authException(AuthException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserExists(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleRoleExists(RoleAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserLinkedToSupplierException.class)
    public ResponseEntity<ErrorMessage> handleLinkedUser(UserLinkedToSupplierException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }


    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDeliveryNotFound(DeliveryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(DeliveryAccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleDeliveryAccessDenied(DeliveryAccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(exception.getMessage()));
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleProductAlreadyExists(ProductAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(ProductAccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleProductAccessDenied(ProductAccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleSupplierNotFound(SupplierNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SupplierAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleSupplierExists(SupplierAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(AdminConflictException.class)
    public ResponseEntity<ErrorMessage> handleAdminExp(AdminConflictException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ErrorMessage> handleEmailUsed(EmailAlreadyUsedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(PhoneAlreadyUsedException.class)
    public ResponseEntity<ErrorMessage> handlePhoneUsed(PhoneAlreadyUsedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(InvalidSupplierDataException.class)
    public ResponseEntity<ErrorMessage> handleInvalidSupplierData(InvalidSupplierDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SupplierUserLinkException.class)
    public ResponseEntity<ErrorMessage> handleSupplierUserLink(SupplierUserLinkException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
