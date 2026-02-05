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

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "delivery_id", nullable = false)
    private DeliveryEntity delivery;
}

