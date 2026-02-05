package com.dazavv.supply.supplymanagementservice.common.utils;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.repository.UserRepository;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class SupplierInitializer implements CommandLineRunner {
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    @Value("${users.logins}")
    private String[] logins;

    @Override
    public void run(String... args) {

        createSupplierIfNotExists("SUP-001", "Supplier1", "+70000000001", "ivan@sup1.ru", logins[1]);
        createSupplierIfNotExists("SUP-002", "Supplier2", "+70000000002", "petr@sup2.ru", logins[2]);
        createSupplierIfNotExists("SUP-003", "Supplier3", "+70000000003", "sergey@sup3.ru", logins[3]);

    }

    private void createSupplierIfNotExists(
            String code,
            String companyName,
            String phone,
            String email,
            String userLogin
    ) {
        if (supplierRepository.existsByCode(code)) {
            log.info("Supplier {} already exists, skipping", code);
            return;
        }

        User user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new IllegalStateException(
                        "User with login " + userLogin + " not found"));

        boolean alreadyLinked = supplierRepository.existsByUserId(user.getId());
        if (alreadyLinked) {
            log.warn("User {} already linked to another supplier, skipping creation for {}", userLogin, code);
            return;
        }

        SupplierEntity supplier = new SupplierEntity();
        supplier.setCode(code);
        supplier.setCompanyName(companyName);
        supplier.setPhoneNumber(phone);
        supplier.setEmail(email);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setUpdatedAt(LocalDateTime.now());
        supplier.setUser(user);

        supplierRepository.save(supplier);

        log.info("Supplier {} created for user {}", code, userLogin);
    }
}
