package com.dazavv.supply.supplymanagementservice.product.repository;

import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
