package com.dazavv.supply.supplymanagementservice.report.service;

import com.dazavv.supply.supplymanagementservice.delivery.service.DeliveryService;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.*;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public DeliveryReportResponse generateReport(
            LocalDateTime start,
            LocalDateTime end,
            Long supplierId,
            String productName
    ) {
        List<ProductReportResponse> aggregated =
                deliveryService.findDeliveriesBetween(start, end);

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

        List<SupplierReportWithTotals> suppliers = grouped.entrySet().stream()
                .map(entry -> {
                    SupplierEntity supplier =
                            supplierService.getSupplierById(entry.getKey());

                    BigDecimal totalWeight = entry.getValue().stream()
                            .map(ProductReportResponse::totalWeight)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalPrice = entry.getValue().stream()
                            .map(ProductReportResponse::totalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new SupplierReportWithTotals(
                            supplier.getId(),
                            supplier.getCompanyName(),
                            totalWeight,
                            totalPrice,
                            entry.getValue()
                    );
                })
                .toList();

        BigDecimal grandWeight = suppliers.stream()
                .map(SupplierReportWithTotals::totalWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grandPrice = suppliers.stream()
                .map(SupplierReportWithTotals::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DeliveryReportResponse(
                new ReportPeriod(start, end),
                suppliers,
                new GrandTotals(grandWeight, grandPrice)
        );
    }


    public byte[] exportReportToCsv(LocalDateTime start,
                                    LocalDateTime end,
                                    Long supplierId,
                                    String productName) {

        DeliveryReportResponse report = generateReport(start, end, supplierId, productName);

        StringBuilder sb = new StringBuilder();

        sb.append("Report period: ")
                .append(start)
                .append(" - ")
                .append(end)
                .append("\n\n");

        sb.append("Supplier ID,Supplier Name,Product Name,Product Type,Total Weight,Total Price\n");

        for (SupplierReportWithTotals supplier : report.suppliers()) {
            for (ProductReportResponse product : supplier.products()) {
                sb.append(supplier.supplierId()).append(",");
                sb.append(supplier.supplierName()).append(",");
                sb.append(product.productName()).append(",");
                sb.append(product.productType()).append(",");
                sb.append(product.totalWeight()).append(",");
                sb.append(product.totalPrice()).append("\n");
            }

            sb.append(",,Supplier totals,,")
                    .append(supplier.totalWeight()).append(",")
                    .append(supplier.totalPrice()).append("\n\n");
        }

        sb.append(",,GRAND TOTALS,,")
                .append(report.grandTotals().totalWeight()).append(",")
                .append(report.grandTotals().totalPrice()).append("\n");

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}
