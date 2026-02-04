package com.dazavv.supply.supplymanagementservice.delivery.entity;

import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "delivery_items")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private DeliveryEntity delivery;
}
