package com.dazavv.supply.supplymanagementservice.auth.controller;

import com.dazavv.supply.supplymanagementservice.auth.dto.*;
import com.dazavv.supply.supplymanagementservice.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                authService.login(request.getLogin(), request.getPassword())
        );
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(
                authService.register(
                request.getLogin(),
                request.getPassword(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPhoneNumber())
        );
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@Valid @RequestBody RefreshJwtRequest request) {
        return ResponseEntity.ok(
                authService.getAccessToken(request.getRefreshToken())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@Valid @RequestBody RefreshJwtRequest request) {
        return ResponseEntity.ok(
                authService.refresh(request.getRefreshToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshJwtRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@Valid @RequestBody AddRoleToUserRequest request) {
        authService.addRoleToUser(request.getLogin(), request.getRole());
        return ResponseEntity.ok("User with login: " + request.getLogin() + " has new role: " + request.getRole());
    }
    //TODO add delete method
}
