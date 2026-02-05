package com.dazavv.supply.supplymanagementservice.auth.controller;

import com.dazavv.supply.supplymanagementservice.auth.dto.request.LoginRequest;
import com.dazavv.supply.supplymanagementservice.auth.dto.request.RefreshJwtRequest;
import com.dazavv.supply.supplymanagementservice.auth.dto.request.RegisterRequest;
import com.dazavv.supply.supplymanagementservice.auth.dto.response.JwtResponse;
import com.dazavv.supply.supplymanagementservice.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
