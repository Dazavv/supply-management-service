package com.dazavv.supply.supplymanagementservice.product.service;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.product.dto.responses.ProductResponse;
import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.product.mapper.ProductMapper;
import com.dazavv.supply.supplymanagementservice.product.repository.ProductRepository;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(User user, String name, String type, BigDecimal price) {
        SupplierEntity supplier = supplierService.getSupplierByUser(user);

        if (productRepository.existsByNameAndTypeAndSupplier(name, type, supplier)) {
            throw new IllegalArgumentException("Product already exists");
        }

        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setType(type);
        product.setPrice(price);
        product.setSupplier(supplier);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Transactional
    public ProductResponse updateProduct(User currentUser, Long productId,
                                         String name, String type, BigDecimal price) {

        SupplierEntity supplier = supplierService.getSupplierByUser(currentUser);

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

        if (productRepository.existsByNameAndTypeAndPriceAndSupplier(product.getName(), product.getType(), price, supplier)) {
            throw new IllegalArgumentException("Product with this name and type already exists for this supplier");
        }

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    public void deleteProduct(User currentUser, Long productId) {
        SupplierEntity supplier = supplierService.getSupplierByUser(currentUser);

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

    public List<ProductResponse> getProductsBySupplier(User user) {
        SupplierEntity supplier = supplierService.getSupplierByUser(user);

        List<ProductEntity> products = productRepository.findAllBySupplier(supplier);
        return productMapper.toProductDtoList(products);
    }

    public ProductResponse getProduct(User user, Long productId) {
        SupplierEntity supplier = supplierService.getSupplierByUser(user);

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this product");
        }
        return productMapper.toProductDto(product);
    }

    public ProductResponse getProduct(Long productId) {
        return productMapper.toProductDto(getProductById(productId));
    }
    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }
}
