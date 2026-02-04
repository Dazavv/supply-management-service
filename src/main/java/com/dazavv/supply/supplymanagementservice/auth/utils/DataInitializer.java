package com.dazavv.supply.supplymanagementservice.auth.utils;

import com.dazavv.supply.supplymanagementservice.auth.entity.AuthUser;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.login:admin}")
    private String adminLogin;

    @Value("${admin.password:admin}")
    private String adminPassword;

    @Value("${admin.name:admin}")
    private String adminName;

    @Value("${admin.surname:admin}")
    private String adminSurname;

    @Value("${admin.email:admin}")
    private String adminEmail;

    @Value("${admin.phoneNumber:admin}")
    private String phoneNumber;

    @Override
    public void run(String... args) {
        if (userRepository.findByLogin(adminLogin).isEmpty()) {
            AuthUser admin = new AuthUser();
            admin.setLogin(adminLogin);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setName(adminName);
            admin.setSurname(adminSurname);
            admin.setEmail(adminEmail);
            admin.setPhoneNumber(phoneNumber);
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
            System.out.println("Admin user created");
        }
    }
}