package com.dazavv.supply.supplymanagementservice.delivery.controller;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.delivery.dto.requests.CreateDeliveryRequest;
import com.dazavv.supply.supplymanagementservice.delivery.dto.requests.UpdateDeliveryRequest;
import com.dazavv.supply.supplymanagementservice.delivery.dto.responses.DeliveryResponse;
import com.dazavv.supply.supplymanagementservice.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<DeliveryResponse> createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request,
            Authentication authentication
    ) {
        User currentUser = userService.getByLogin(authentication.getName());
        DeliveryResponse delivery = deliveryService.createDelivery(
                currentUser,
                request.deliveryDateTime(),
                request.deliveryAddress(),
                request.items(),
                request.comment()
        );
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(delivery);
    }

    @PutMapping("/{deliveryId}")
    @PreAuthorize("hasAnyAuthority('SUPPLIER')")
    public ResponseEntity<DeliveryResponse> updateDelivery(
            @PathVariable Long deliveryId,
            @Valid @RequestBody UpdateDeliveryRequest request,
            Authentication authentication
    ) {
        User currentUser = userService.getByLogin(authentication.getName());
        DeliveryResponse delivery = deliveryService.updateDelivery(
                currentUser,
                deliveryId,
                request.deliveryDateTime(),
                request.deliveryAddress(),
                request.items(),
                request.status(),
                request.comment()
        );
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/{deliveryId}")
    @PreAuthorize("hasAnyAuthority('SUPPLIER', 'ADMIN')")
    public ResponseEntity<DeliveryResponse> getDelivery(
            @PathVariable Long deliveryId,
            Authentication authentication
    ) {
        User currentUser = userService.getByLogin(authentication.getName());
        DeliveryResponse delivery = deliveryService.getDeliveryById(currentUser, deliveryId);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPPLIER', 'ADMIN')")
    public ResponseEntity<List<DeliveryResponse>> getDeliveries(Authentication authentication) {
        User currentUser = userService.getByLogin(authentication.getName());
        List<DeliveryResponse> deliveries = deliveryService.getDeliveries(currentUser);
        return ResponseEntity.ok(deliveries);
    }

    @DeleteMapping("/{deliveryId}")
    @PreAuthorize("hasAnyAuthority('SUPPLIER', 'ADMIN')")
    public ResponseEntity<Void> deleteDelivery(
            @PathVariable Long deliveryId,
            Authentication authentication
    ) {
        User currentUser = userService.getByLogin(authentication.getName());
        deliveryService.deleteDelivery(currentUser, deliveryId);
        return ResponseEntity.noContent().build();
    }
}