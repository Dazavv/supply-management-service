package com.dazavv.supply.supplymanagementservice.common.exception;

import com.dazavv.supply.supplymanagementservice.auth.exception.AuthException;
import com.dazavv.supply.supplymanagementservice.auth.exception.RoleAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserLinkedToSupplierException;
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



}
