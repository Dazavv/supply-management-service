package com.dazavv.supply.supplymanagementservice.supplier.controller;

import com.dazavv.supply.supplymanagementservice.supplier.dto.requests.CreateSupplierRequest;
import com.dazavv.supply.supplymanagementservice.supplier.dto.requests.UpdateSupplierRequest;
import com.dazavv.supply.supplymanagementservice.supplier.dto.responses.SupplierResponse;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SupplierResponse> getAllSupplierById(@PathVariable @Min(1) Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.getSupplier(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody CreateSupplierRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplierService.createSupplier(
                        request.userId(),
                        request.companyName(),
                        request.code(),
                        request.email(),
                        request.phoneNumber())
                );
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SupplierResponse> updateSupplier(@Valid @RequestBody UpdateSupplierRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.updateSupplier(
                        request.supplierId(),
                        request.companyName(),
                        request.email(),
                        request.phoneNumber())
                );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplierById(@PathVariable @Min(1) Long id) {
        supplierService.deleteSupplierById(id);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<SupplierResponse>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.getAllUsers(PageRequest.of(page, size))
                );
    }
}
