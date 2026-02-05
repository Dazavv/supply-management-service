package com.dazavv.supply.supplymanagementservice.delivery.entity;

import com.dazavv.supply.supplymanagementservice.delivery.utils.DeliveryStatus;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "delivery")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime deliveryDateTime;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<DeliveryItemEntity> items;

    @NotBlank
    @Size(max = 255)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private BigDecimal totalAmount;

    @Size(max = 255)
    private String comment;
}
