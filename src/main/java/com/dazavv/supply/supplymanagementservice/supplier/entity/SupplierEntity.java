package com.dazavv.supply.supplymanagementservice.supplier.entity;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
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
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank
    @Size(max = 30)
    @Column(name = "company_name", nullable = false, length = 30)
    private String companyName;

    @NotBlank
    @Size(max = 12)
    @Column(name = "phone_number", nullable = false, length = 12)
    private String phoneNumber;

    @NotBlank
    @Size(max = 30)
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "auth_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "supplier")
    private List<DeliveryEntity> deliveries;
}
