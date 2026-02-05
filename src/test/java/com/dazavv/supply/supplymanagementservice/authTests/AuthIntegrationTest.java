package com.dazavv.supply.supplymanagementservice.authTests;

import com.dazavv.supply.supplymanagementservice.auth.dto.response.JwtResponse;
import com.dazavv.supply.supplymanagementservice.auth.service.AuthService;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AuthIntegrationTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Test
    void registerAndLogin_flow() {

        JwtResponse register = authService.register(
                "intUser",
                "pass",
                "name",
                "surname",
                "int@mail.com",
                "999"
        );

        assertNotNull(register.getAccessToken());

        JwtResponse login = authService.login("intUser", "pass");

        assertNotNull(login.getAccessToken());
    }

    @Test
    void refreshToken_flow() {
        JwtResponse register = authService.register(
                "refUser",
                "pass",
                "n",
                "s",
                "r@mail.com",
                "777"
        );

        JwtResponse refreshed =
                authService.refresh(register.getRefreshToken());

        assertNotNull(refreshed.getAccessToken());
        assertNotNull(refreshed.getRefreshToken());
    }
}
