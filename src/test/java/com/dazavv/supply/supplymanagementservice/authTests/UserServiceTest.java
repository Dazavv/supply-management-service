package com.dazavv.supply.supplymanagementservice.authTests;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.exception.RoleAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserLinkedToSupplierException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserNotFoundException;
import com.dazavv.supply.supplymanagementservice.auth.mapper.UserMapper;
import com.dazavv.supply.supplymanagementservice.auth.repository.UserRepository;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void getByLogin_success() {
        User user = new User();
        user.setLogin("test");

        when(userRepository.findByLogin("test")).thenReturn(Optional.of(user));

        User result = userService.getByLogin("test");

        assertEquals("test", result.getLogin());
    }

    @Test
    void getByLogin_notFound() {
        when(userRepository.findByLogin("bad")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getByLogin("bad"));
    }

    @Test
    void addNewRole_alreadyExists() {
        User user = new User();
        user.getRoles().add(Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RoleAlreadyExistsException.class,
                () -> userService.addNewRole(1L, Role.ADMIN));
    }

    @Test
    void deleteUser_linkedToSupplier() {
        when(supplierRepository.existsByUserId(1L)).thenReturn(true);

        assertThrows(UserLinkedToSupplierException.class,
                () -> userService.deleteUserById(1L));
    }

    @Test
    void checkEmailUniqueness_fail() {
        when(userRepository.existsByEmail("mail")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.checkEmailUniqueness("mail"));
    }
}
