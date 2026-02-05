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
        d.supplier.id,
        p.name,
        p.type,
        SUM(i.weight),
        SUM(i.price)
    )
    FROM DeliveryItemEntity i
    JOIN i.delivery d
    JOIN i.product p
    WHERE d.deliveryDateTime BETWEEN :start AND :end
    GROUP BY d.supplier.id, p.name, p.type
    """)
    List<ProductReportResponse> aggregateProductsBySupplier(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
