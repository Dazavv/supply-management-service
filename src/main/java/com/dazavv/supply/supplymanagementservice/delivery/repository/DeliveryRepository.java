package com.dazavv.supply.supplymanagementservice.delivery.repository;

import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
