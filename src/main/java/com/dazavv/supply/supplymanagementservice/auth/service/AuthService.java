package com.dazavv.supply.supplymanagementservice.auth.service;

import com.dazavv.supply.supplymanagementservice.auth.dto.JwtResponse;
import com.dazavv.supply.supplymanagementservice.auth.entity.AuthUser;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.exception.AuthException;
import com.dazavv.supply.supplymanagementservice.auth.jwt.JwtAuthentication;
import com.dazavv.supply.supplymanagementservice.auth.jwt.JwtProvider;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserService authUserService;
    private final SupplierService supplierService;

    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(String login, String password) {
        final AuthUser user = authUserService.getByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Password is wrong");
        }
    }

    public JwtResponse register(String login, String password, String name, String surname, String email, String phoneNumber) {

        authUserService.validateUserUniqueness(login, email, phoneNumber);
        supplierService.validateSupplierUniqueness(email, phoneNumber);

        AuthUser user = new AuthUser();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(Collections.singleton(Role.VIEWER));

        authUserService.save(user);

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getLogin(), refreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                refreshStorage.remove(login);
            }
        }
    }

    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final AuthUser user = getUser(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final AuthUser user = getUser(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("JWT was not valid");
    }
    public void addRoleToUser(String login, Role role) {
        final AuthUser user = authUserService.getByLogin(login);
        authUserService.addNewRole(user.getId(), role);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}