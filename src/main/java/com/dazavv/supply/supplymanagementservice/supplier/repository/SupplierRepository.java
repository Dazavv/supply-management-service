package com.dazavv.supply.supplymanagementservice.supplier.repository;

import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
}
