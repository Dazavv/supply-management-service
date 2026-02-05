package com.dazavv.supply.supplymanagementservice.auth.utils;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${users.logins}")
    private String[] logins;

    @Value("${users.passwords}")
    private String[] passwords;

    @Value("${users.names}")
    private String[] names;

    @Value("${users.surnames}")
    private String[] surnames;

    @Value("${users.emails}")
    private String[] emails;

    @Value("${users.phones}")
    private String[] phones;

    @Value("${users.roles}")
    private String[] roles;

    @Override
    public void run(String... args) {

        for (int i = 0; i < logins.length; i++) {
            String login = logins[i];
            if (userRepository.findByLogin(login).isEmpty()) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(passwordEncoder.encode(passwords[i]));
                user.setName(names[i]);
                user.setSurname(surnames[i]);
                user.setEmail(emails[i]);
                user.setPhoneNumber(phones[i]);
                user.setRoles(Collections.singleton(Role.valueOf(roles[i])));
                userRepository.save(user);
                System.out.println("user created: " + login);
            }
        }
    }
}
