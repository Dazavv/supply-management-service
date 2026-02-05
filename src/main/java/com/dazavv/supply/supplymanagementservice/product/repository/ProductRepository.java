package com.dazavv.supply.supplymanagementservice.product.repository;

import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllBySupplier(SupplierEntity supplier);

    boolean existsByNameAndTypeAndPriceAndSupplier(String name, String type, BigDecimal price, SupplierEntity supplier);

    boolean existsByNameAndTypeAndSupplier(String name, String type, SupplierEntity supplier);
}
