package com.dazavv.supply.supplymanagementservice.supplierTests;

import com.dazavv.supply.supplymanagementservice.auth.jwt.JwtFilter;
import com.dazavv.supply.supplymanagementservice.auth.jwt.JwtProvider;
import com.dazavv.supply.supplymanagementservice.supplier.controller.SupplierController;
import com.dazavv.supply.supplymanagementservice.supplier.dto.requests.CreateSupplierRequest;
import com.dazavv.supply.supplymanagementservice.supplier.dto.responses.SupplierResponse;
import com.dazavv.supply.supplymanagementservice.supplier.service.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplierController.class)
@Import(ObjectMapper.class)
class SupplierControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    SupplierService supplierService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getSupplierById_success() throws Exception {

        when(supplierService.createSupplier(any(), any(), any(), any(), any()))
                .thenReturn(new SupplierResponse(
                        1L,
                        "CODE",
                        "Comp",
                        "mail@mail.com",
                        "123",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ));


        mockMvc.perform(get("/api/v1/supplier/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "VIEWER")
    void createSupplier_forbiddenForNonAdmin() throws Exception {

        CreateSupplierRequest request =
                new CreateSupplierRequest(1L, "Comp", "CODE", "mail@mail.com", "123");

        mockMvc.perform(post("/api/v1/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}

