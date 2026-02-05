package com.dazavv.supply.supplymanagementservice.delivery.service;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.delivery.dto.requests.DeliveryItemRequest;
import com.dazavv.supply.supplymanagementservice.delivery.dto.responses.DeliveryResponse;
import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryItemEntity;
import com.dazavv.supply.supplymanagementservice.delivery.exception.DeliveryAccessDeniedException;
import com.dazavv.supply.supplymanagementservice.delivery.exception.DeliveryNotFoundException;
import com.dazavv.supply.supplymanagementservice.delivery.mapper.DeliveryItemMapper;
import com.dazavv.supply.supplymanagementservice.delivery.mapper.DeliveryMapper;
import com.dazavv.supply.supplymanagementservice.delivery.repository.DeliveryRepository;
import com.dazavv.supply.supplymanagementservice.delivery.utils.DeliveryStatus;
import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;
import com.dazavv.supply.supplymanagementservice.product.service.ProductService;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.ProductReportResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final DeliveryMapper deliveryMapper;

    @Transactional
    public DeliveryResponse createDelivery(User currentUser,
                                           LocalDateTime deliveryDateTime,
                                           String deliveryAddress,
                                           List<DeliveryItemRequest> items,
                                           String comment) {
        SupplierEntity supplier = supplierService.getSupplierByUser(currentUser);

        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDateTime(deliveryDateTime);
        delivery.setStatus(DeliveryStatus.CREATED);
        delivery.setComment(comment);
        delivery.setDeliveryAddress(deliveryAddress);

        List<DeliveryItemEntity> deliveryItems = items.stream().map(itemReq -> {
            ProductEntity product = productService.getProductById(itemReq.productId());

            DeliveryItemEntity item = new DeliveryItemEntity();
            item.setProduct(product);
            item.setWeight(itemReq.weight());
            item.setPrice(itemReq.price());
            item.setDelivery(delivery);
            return item;
        }).collect(Collectors.toList());

        delivery.setItems(deliveryItems);

        BigDecimal totalAmount = deliveryItems.stream()
                .map(i -> i.getPrice().multiply(i.getWeight()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        delivery.setTotalAmount(totalAmount);
        deliveryRepository.save(delivery);
        return deliveryMapper.toDeliveryDto(delivery);
    }

    @Transactional
    public DeliveryResponse updateDelivery(User currentUser,
                                           Long deliveryId,
                                           LocalDateTime deliveryDateTime,
                                           String deliveryAddress,
                                           List<DeliveryItemRequest> items,
                                           DeliveryStatus status,
                                           String comment) {
        DeliveryEntity delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() ->
                        new DeliveryNotFoundException(
                                "Delivery not found with id: " + deliveryId
                        ));

        if (!delivery.getSupplier().getUser().getId().equals(currentUser.getId())) {
            throw new DeliveryAccessDeniedException("Cannot update delivery of another supplier");
        }

        if (deliveryDateTime != null) delivery.setDeliveryDateTime(deliveryDateTime);
        if (comment != null && !comment.isBlank()) delivery.setComment(comment);
        if (status != null) delivery.setStatus(status);
        if (deliveryAddress != null && !deliveryAddress.isBlank()) delivery.setDeliveryAddress(deliveryAddress);
        if (items != null && !items.isEmpty()) {
            delivery.getItems().clear();

            List<DeliveryItemEntity> deliveryItems = items.stream().map(itemReq -> {
                ProductEntity product = productService.getProductById(itemReq.productId());

                DeliveryItemEntity item = new DeliveryItemEntity();
                item.setProduct(product);
                item.setWeight(itemReq.weight());
                item.setPrice(itemReq.price());
                item.setDelivery(delivery);
                return item;
            }).collect(Collectors.toList());

            delivery.setItems(deliveryItems);

            BigDecimal totalAmount = deliveryItems.stream()
                    .map(i -> i.getPrice().multiply(i.getWeight()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            delivery.setTotalAmount(totalAmount);
        }

        deliveryRepository.save(delivery);
        return deliveryMapper.toDeliveryDto(delivery);
    }

    public DeliveryResponse getDeliveryById(User currentUser, Long deliveryId) {
        DeliveryEntity delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() ->
                        new DeliveryNotFoundException(
                                "Delivery not found with id: " + deliveryId
                        ));

        if (currentUser.getRoles().contains(Role.SUPPLIER)
                && !delivery.getSupplier().getUser().getId().equals(currentUser.getId())) {
            throw new DeliveryAccessDeniedException("Cannot view delivery of another supplier");
        }
        return deliveryMapper.toDeliveryDto(delivery);
    }

    public List<DeliveryResponse> getDeliveries(User currentUser) {

        List<DeliveryEntity> deliveries;

        if (currentUser.getRoles().contains(Role.ADMIN)) {
            deliveries = deliveryRepository.findAll();
        } else {
            SupplierEntity supplier = supplierService.getSupplierByUser(currentUser);
            deliveries = deliveryRepository.findAllBySupplier(supplier);
        }

        return deliveryMapper.toDeliveryDtoList(deliveries);
    }

    @Transactional
    public void deleteDelivery(User currentUser, Long deliveryId) {
        DeliveryEntity delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + deliveryId));

        if (currentUser.getRoles().contains(Role.SUPPLIER) && !delivery.getSupplier().getUser().getId().equals(currentUser.getId())) {
            throw new DeliveryAccessDeniedException("Cannot delete delivery of another supplier");
        }
        deliveryRepository.delete(delivery);
    }

    public List<ProductReportResponse> findDeliveriesBetween(LocalDateTime start,
                                                             LocalDateTime end) {
        return deliveryRepository.aggregateProductsBySupplier(start, end);
    }
}
