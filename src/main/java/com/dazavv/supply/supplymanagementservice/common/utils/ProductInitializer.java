package com.dazavv.supply.supplymanagementservice.common.utils;

import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.product.repository.ProductRepository;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class ProductInitializer implements CommandLineRunner {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        createProduct("SUP-001", "Apple Red", "APPLE", BigDecimal.valueOf(100.0));
        createProduct("SUP-001", "Apple Green", "APPLE", BigDecimal.valueOf(110));
        createProduct("SUP-001", "Pear Conference", "PEAR", BigDecimal.valueOf(120));
        createProduct("SUP-001", "Pear Williams", "PEAR", BigDecimal.valueOf(130));

        createProduct("SUP-002", "Apple Red", "APPLE", BigDecimal.valueOf(105));
        createProduct("SUP-002", "Apple Golden", "APPLE", BigDecimal.valueOf(115));
        createProduct("SUP-002", "Pear Conference", "PEAR", BigDecimal.valueOf(125));
        createProduct("SUP-002", "Pear Abate", "PEAR", BigDecimal.valueOf(135));

        createProduct("SUP-003", "Apple Fuji", "APPLE", BigDecimal.valueOf(102));
        createProduct("SUP-003", "Apple Granny", "APPLE", BigDecimal.valueOf(112));
        createProduct("SUP-003", "Pear Conference", "PEAR", BigDecimal.valueOf(122));
        createProduct("SUP-003", "Pear Williams", "PEAR", BigDecimal.valueOf(132));
    }

    private void createProduct(String supplierCode, String name, String type, BigDecimal price) {
        Optional<SupplierEntity> supplierOpt = supplierRepository.findByCode(supplierCode);
        if (supplierOpt.isEmpty()) {
            log.warn("Supplier with code {} not found, product {} not created", supplierCode, name);
            return;
        }
        SupplierEntity supplier = supplierOpt.get();
        if (!productRepository.existsByNameAndTypeAndPriceAndSupplier(name, type, price, supplier)) {
            ProductEntity product = new ProductEntity();
            product.setName(name);
            product.setType(type);
            product.setPrice(price);
            product.setSupplier(supplier);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            productRepository.save(product);
            log.info("Product {} created for supplier {}", name, supplierCode);
        }
    }
}
