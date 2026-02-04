package com.dazavv.supply.supplymanagementservice.product.repository;

import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByNameAndTypeAndSupplier(String name, String type, SupplierEntity supplier);

    List<ProductEntity> findAllBySupplier(SupplierEntity supplier);
}
