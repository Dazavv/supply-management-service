package com.dazavv.supply.supplymanagementservice.delivery.repository;

import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.ProductReportResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    List<DeliveryEntity> findAllBySupplier(SupplierEntity supplier);

    @Query("""
    SELECT new com.dazavv.supply.supplymanagementservice.report.dto.responses.ProductReportResponse(
        s.id,
        p.name,
        p.type,
        CAST(SUM(di.weight) AS bigdecimal),
        CAST(SUM(di.price * di.weight) AS bigdecimal)
    )
    FROM DeliveryItemEntity di
    JOIN di.product p
    JOIN di.delivery d
    JOIN d.supplier s
    WHERE d.deliveryDateTime BETWEEN :start AND :end
    GROUP BY s.id, p.name, p.type
    """)
    List<ProductReportResponse> aggregateProductsBySupplier(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


}
