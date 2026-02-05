package com.dazavv.supply.supplymanagementservice.report.controller;

import com.dazavv.supply.supplymanagementservice.report.dto.requests.ReportRequest;
import com.dazavv.supply.supplymanagementservice.report.dto.responses.SupplierReportResponse;
import com.dazavv.supply.supplymanagementservice.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/deliveries")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SupplierReportResponse>> getDeliveryReport(@Valid @RequestBody ReportRequest request) {
        List<SupplierReportResponse> report = reportService.generateReport(
                request.startDate(),
                request.endDate(),
                null,
                null
        );
        return ResponseEntity.ok(report);
    }

    @PostMapping("/deliveries/supplier/{supplierId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SupplierReportResponse>> getDeliveryReportBySupplier(
            @PathVariable Long supplierId,
            @Valid @RequestBody ReportRequest request) {

        List<SupplierReportResponse> report = reportService.generateReport(
                request.startDate(),
                request.endDate(),
                supplierId,
                null
        );
        return ResponseEntity.ok(report);
    }

    @PostMapping("/deliveries/export/csv")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> exportDeliveryReportCsv(@Valid @RequestBody ReportRequest request) {
        byte[] csvData = reportService.exportReportToCsv(
                request.startDate(),
                request.endDate(),
                null,
                null
        );

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=delivery_report.csv")
                .header("Content-Type", "text/csv")
                .body(csvData);
    }

}
