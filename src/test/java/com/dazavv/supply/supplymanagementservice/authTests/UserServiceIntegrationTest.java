package com.dazavv.supply.supplymanagementservice.authTests;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    void createAndFindUser() {
        User user = new User();
        user.setLogin("user1");
        user.setEmail("user@mail.com");
        user.setPhoneNumber("123");
        user.setPassword("pass123");
        user.setName("Ivan");
        user.setSurname("Ivanov");
        user.setRoles(Collections.singleton(Role.VIEWER));

        userService.save(user);

        User found = userService.getByLogin("user1");

        assertEquals("user1", found.getLogin());
    }


    @Test
    void uniquenessValidation() {
        User user = new User();
        user.setLogin("unique");
        user.setEmail("unique@mail.com");
        user.setPhoneNumber("555");
        user.setPassword("pass123");
        user.setName("Petr");
        user.setSurname("Petrov");
        user.setRoles(Collections.singleton(Role.VIEWER));

        userService.save(user);

        assertThrows(
                Exception.class,
                () -> userService.validateUserUniqueness(
                        "unique", "unique@mail.com", "555"
                )
        );
    }
}
