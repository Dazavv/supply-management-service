package com.dazavv.supply.supplymanagementservice.delivery.repository;

import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryItemRepository extends JpaRepository<DeliveryItemEntity, Long> {
}
