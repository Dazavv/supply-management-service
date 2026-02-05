package com.dazavv.supply.supplymanagementservice.authTests;

import com.dazavv.supply.supplymanagementservice.auth.dto.response.JwtResponse;
import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.exception.AuthException;
import com.dazavv.supply.supplymanagementservice.auth.jwt.JwtProvider;
import com.dazavv.supply.supplymanagementservice.auth.service.AuthService;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private SupplierService supplierService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testUser");
        user.setPassword("encodedPassword");
        user.setRoles(Collections.singleton(Role.VIEWER));
    }

    @Test
    void login_success() {
        when(userService.getByLogin("testUser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtProvider.generateAccessToken(user)).thenReturn("access");
        when(jwtProvider.generateRefreshToken(user)).thenReturn("refresh");

        JwtResponse response = authService.login("testUser", "password");

        assertNotNull(response);
        assertEquals("access", response.getAccessToken());
        assertEquals("refresh", response.getRefreshToken());
    }

    @Test
    void login_wrongPassword() {
        when(userService.getByLogin("testUser")).thenReturn(user);
        when(passwordEncoder.matches("bad", "encodedPassword")).thenReturn(false);

        assertThrows(AuthException.class,
                () -> authService.login("testUser", "bad"));
    }

    @Test
    void register_success() {
        when(jwtProvider.generateAccessToken(any())).thenReturn("access");
        when(jwtProvider.generateRefreshToken(any())).thenReturn("refresh");
        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        JwtResponse response = authService.register(
                "newUser", "pass", "name", "surname", "email@mail.com", "123"
        );

        verify(userService).save(any(User.class));
        verify(userService).validateUserUniqueness(any(), any(), any());
        verify(supplierService).validateSupplierUniqueness(any(), any());

        assertEquals("access", response.getAccessToken());
        assertEquals("refresh", response.getRefreshToken());
    }

    @Test
    void refresh_invalidToken() {
        when(jwtProvider.validateRefreshToken("bad")).thenReturn(false);

        assertThrows(AuthException.class,
                () -> authService.refresh("bad"));
    }

    @Test
    void getAccessToken_invalid() {
        when(jwtProvider.validateRefreshToken("bad")).thenReturn(false);

        JwtResponse response = authService.getAccessToken("bad");

        assertNull(response.getAccessToken());
    }

    @Test
    void logout_success() {
        Claims claims = mock(Claims.class);

        when(jwtProvider.validateRefreshToken("token")).thenReturn(true);
        when(jwtProvider.getRefreshClaims("token")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("testUser");

        authService.logout("token");

        verify(jwtProvider).validateRefreshToken("token");
    }
}
