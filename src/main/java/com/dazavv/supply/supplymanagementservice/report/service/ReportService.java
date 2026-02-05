package com.dazavv.supply.supplymanagementservice.report.service;

import com.dazavv.supply.supplymanagementservice.delivery.service.DeliveryService;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.ProductReportResponse;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.SupplierReportResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DeliveryService deliveryService;
    private final SupplierService supplierService;

    public List<SupplierReportResponse> generateReport(
            LocalDateTime start,
            LocalDateTime end,
            Long supplierId,
            String productName
    ) {
        List<ProductReportResponse> aggregated = deliveryService.findDeliveriesBetween(start, end);

        if (supplierId != null) {
            aggregated = aggregated.stream()
                    .filter(p -> p.supplierId().equals(supplierId))
                    .toList();
        }

        if (productName != null) {
            aggregated = aggregated.stream()
                    .filter(p -> p.productName().equals(productName))
                    .toList();
        }

        Map<Long, List<ProductReportResponse>> grouped = aggregated.stream()
                .collect(Collectors.groupingBy(ProductReportResponse::supplierId));

        return grouped.entrySet().stream()
                .map(entry -> {
                    SupplierEntity supplier = supplierService.getSupplierById(entry.getKey());
                    return new SupplierReportResponse(
                            supplier.getId(),
                            supplier.getCompanyName(),
                            entry.getValue()
                    );
                })
                .toList();
    }

    public byte[] exportReportToCsv(LocalDateTime start,
                                    LocalDateTime end,
                                    Long supplierId,
                                    String productName) {

        List<SupplierReportResponse> report = generateReport(start, end, supplierId, productName);

        StringBuilder sb = new StringBuilder();

        sb.append("Supplier ID,Supplier Name,Product Name,Product Type,Total Weight,Total Price\n");

        for (SupplierReportResponse supplier : report) {
            for (ProductReportResponse product : supplier.products()) {
                sb.append(supplier.supplierId()).append(",");
                sb.append(supplier.supplierName()).append(",");
                sb.append(product.productName()).append(",");
                sb.append(product.productType()).append(",");
                sb.append(product.totalWeight()).append(",");
                sb.append(product.totalPrice()).append("\n");
            }
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
