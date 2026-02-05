package com.dazavv.supply.supplymanagementservice.product.controller;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.product.dto.requests.CreateProductRequest;
import com.dazavv.supply.supplymanagementservice.product.dto.requests.UpdateProductRequest;
import com.dazavv.supply.supplymanagementservice.product.dto.responses.ProductResponse;
import com.dazavv.supply.supplymanagementservice.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request,
                                                         Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());
        ProductResponse product = productService.createProduct(
                currentUser,
                request.name(),
                request.type(),
                request.price()
        );

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(product);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody UpdateProductRequest request,
                                                         Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());
        ProductResponse product = productService.updateProduct(
                currentUser,
                request.productId(),
                request.name(),
                request.type(),
                request.price()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) Long productId,
                                              Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());

        productService.deleteProduct(currentUser, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/supplier/{supplierId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProductResponse>> getProductsBySupplier(@PathVariable @Min(1) Long supplierId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductsBySupplier(supplierId));
    }

    @GetMapping("/supplier/me")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<List<ProductResponse>> getMyProducts(Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductsBySupplier(currentUser));
    }

    @GetMapping("/supplier/me/{productId}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<ProductResponse> getMyProductById(@PathVariable @Min(1) Long productId,
                                                                  Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProduct(currentUser, productId));
    }

    @GetMapping("/admin/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponse> geProductById(@PathVariable @Min(1) Long productId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProduct(productId));
    }

}
