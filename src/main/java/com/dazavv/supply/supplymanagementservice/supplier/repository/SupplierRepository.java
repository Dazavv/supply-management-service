package com.dazavv.supply.supplymanagementservice.supplier.repository;

import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    boolean existsByCode(String code);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long supplierId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long supplierId);

    SupplierEntity findByAuthUserId(Long id);
}
