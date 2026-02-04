package com.dazavv.supply.supplymanagementservice.product.service;

import com.dazavv.supply.supplymanagementservice.auth.entity.AuthUser;
import com.dazavv.supply.supplymanagementservice.product.dto.responses.ProductResponse;
import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.product.mapper.ProductMapper;
import com.dazavv.supply.supplymanagementservice.product.repository.ProductRepository;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(AuthUser user, String name, String type, BigDecimal price) {
        SupplierEntity supplier = supplierService.getSupplierByUserId(user.getId());

        if (productRepository.existsByNameAndTypeAndSupplier(name, type, supplier)) {
            throw new IllegalArgumentException("Product already exists");
        }

        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setType(type);
        product.setPrice(price);
        product.setSupplier(supplier);

        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Transactional
    public ProductResponse updateProduct(AuthUser currentUser, Long productId,
                                         String name, String type, BigDecimal price) {

        SupplierEntity supplier = supplierService.getSupplierByUserId(currentUser.getId());

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("You are not allowed to update this product");
        }

        if (name != null && !name.isBlank()) {
            product.setName(name);
        }

        if (type != null && !type.isBlank()) {
            product.setType(type);
        }

        if (price != null) {
            product.setPrice(price);
        }

        if (productRepository.existsByNameAndTypeAndSupplier(product.getName(), product.getType(), supplier)) {
            throw new IllegalArgumentException("Product with this name and type already exists for this supplier");
        }

        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    public void deleteProduct(AuthUser currentUser, Long productId) {
        SupplierEntity supplier = supplierService.getSupplierByUserId(currentUser.getId());

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this product");
        }

        productRepository.delete(product);
    }

    public List<ProductResponse> getProductsBySupplier(Long supplierId) {
        SupplierEntity supplier = supplierService.getSupplierById(supplierId);

        List<ProductEntity> products = productRepository.findAllBySupplier(supplier);

        return productMapper.toProductDtoList(products);
    }

    public List<ProductResponse> getProductsBySupplier(AuthUser user) {
        SupplierEntity supplier = supplierService.getSupplierByUserId(user.getId());

        List<ProductEntity> products = productRepository.findAllBySupplier(supplier);

        return productMapper.toProductDtoList(products);
    }

    public ProductResponse getProductById(AuthUser user, Long productId) {
        SupplierEntity supplier = supplierService.getSupplierByUserId(user.getId());

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this product");
        }
        return productMapper.toProductDto(product);
    }

    public ProductResponse getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        return productMapper.toProductDto(product);
    }
}
