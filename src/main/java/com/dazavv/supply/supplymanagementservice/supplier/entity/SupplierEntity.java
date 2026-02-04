package com.dazavv.supply.supplymanagementservice.supplier.entity;

import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String code; // Внутренний код поставщика (например SUP-001)

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String surname;

    @NotBlank
    @Size(max = 12)
    private String phone;

    @Size(max = 30)
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "supplier")
    private List<DeliveryEntity> deliveries;
}
