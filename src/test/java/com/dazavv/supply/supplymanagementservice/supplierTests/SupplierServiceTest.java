package com.dazavv.supply.supplymanagementservice.supplierTests;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.supplier.dto.responses.SupplierResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.exception.AdminConflictException;
import com.dazavv.supply.supplymanagementservice.supplier.exception.SupplierAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.supplier.mapper.SupplierMapper;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    SupplierMapper supplierMapper;

    @Mock
    UserService userService;

    @InjectMocks
    SupplierService supplierService;

    private User createUser(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setEmail("test@mail.com");
        user.setPhoneNumber("123");
        user.setRoles(Set.of(role));
        return user;
    }

    @Test
    void createSupplier_success() {
        User user = createUser(1L, Role.VIEWER);

        when(userService.getUserById(1L)).thenReturn(user);
        when(supplierRepository.existsByCode("CODE")).thenReturn(false);
        when(supplierMapper.toSupplierDto(any())).thenReturn(
                new SupplierResponse(
                        1L,
                        "CODE",
                        "Company",
                        "mail@mail.com",
                        "123",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        );

        SupplierResponse response = supplierService.createSupplier(
                1L,
                "Company",
                "CODE",
                null,
                null
        );

        assertNotNull(response);
        verify(supplierRepository).save(any(SupplierEntity.class));
        verify(userService).addNewRole(1L, Role.SUPPLIER);
    }

    @Test
    void createSupplier_userAlreadySupplier() {
        User user = createUser(1L, Role.SUPPLIER);

        when(userService.getUserById(1L)).thenReturn(user);

        assertThrows(
                SupplierAlreadyExistsException.class,
                () -> supplierService.createSupplier(1L, "C", "CODE", null, null)
        );
    }

    @Test
    void createSupplier_adminCannotBeSupplier() {
        User user = createUser(1L, Role.ADMIN);

        when(userService.getUserById(1L)).thenReturn(user);

        assertThrows(
                AdminConflictException.class,
                () -> supplierService.createSupplier(1L, "C", "CODE", null, null)
        );
    }

    @Test
    void createSupplier_duplicateCode() {
        User user = createUser(1L, Role.VIEWER);

        when(userService.getUserById(1L)).thenReturn(user);
        when(supplierRepository.existsByCode("CODE")).thenReturn(true);

        assertThrows(
                SupplierAlreadyExistsException.class,
                () -> supplierService.createSupplier(1L, "C", "CODE", null, null)
        );
    }

    @Test
    void deleteSupplier_notFound() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> supplierService.deleteSupplierById(1L)
        );
    }

    @Test
    void deleteSupplier_success() {
        when(supplierRepository.existsById(1L)).thenReturn(true);

        supplierService.deleteSupplierById(1L);

        verify(supplierRepository).deleteById(1L);
    }
}
