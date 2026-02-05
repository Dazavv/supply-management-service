package com.dazavv.supply.supplymanagementservice.supplier.service;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import com.dazavv.supply.supplymanagementservice.common.exception.SupplierNotFoundException;
import com.dazavv.supply.supplymanagementservice.supplier.dto.responses.SupplierResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import com.dazavv.supply.supplymanagementservice.supplier.mapper.SupplierMapper;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final UserService userService;

    @Transactional
    public SupplierResponse createSupplier(
            Long userId,
            String companyName,
            String code,
            String email,
            String phoneNumber
    ) {
        User user = userService.getUserById(userId);

        if (user.getRoles().contains(Role.SUPPLIER)) {
            throw new IllegalArgumentException("User is already a supplier");
        }
        if (user.getRoles().contains(Role.ADMIN)) {
            throw new IllegalArgumentException("Admin can't be a supplier");
        }
        if (supplierRepository.existsByCode(code)) {
            throw new IllegalArgumentException("Supplier code already exists");
        }

        String finalEmail = resolveEmailForSupplier(user, email);
        String finalPhone = resolvePhoneForSupplier(user, phoneNumber);

        SupplierEntity supplier = new SupplierEntity();

        supplier.setCompanyName(companyName);
        supplier.setCode(code);
        supplier.setEmail(finalEmail);
        supplier.setPhoneNumber(finalPhone);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setUpdatedAt(LocalDateTime.now());
        supplier.setUser(user);

        supplierRepository.save(supplier);
        userService.addNewRole(userId, Role.SUPPLIER);

        return supplierMapper.toSupplierDto(supplier);
    }

    @Transactional
    public SupplierResponse updateSupplier(
            Long supplierId,
            String companyName,
            String email,
            String phoneNumber
    ) {

        SupplierEntity supplier = getSupplierById(supplierId);

        if (companyName != null && !companyName.isBlank()) {
            supplier.setCompanyName(companyName);
        }

        if (email != null && !email.isBlank()) {

            if (!email.equals(supplier.getEmail())) {
                boolean usedByOtherSupplier = supplierRepository.existsByEmailAndIdNot(email, supplierId);
                boolean usedByUser = userService.checkEmailUniqueness(email);

                if (usedByOtherSupplier || usedByUser) {
                    throw new IllegalArgumentException("Email already used by another user or supplier");
                }
                supplier.setEmail(email);
            }
        }

        if (phoneNumber != null && !phoneNumber.isBlank()) {

            if (!phoneNumber.equals(supplier.getPhoneNumber())) {
                boolean usedByOtherSupplier = supplierRepository.existsByPhoneNumberAndIdNot(phoneNumber, supplierId);
                boolean usedByUser = userService.checkPhoneUniqueness(phoneNumber);

                if (usedByOtherSupplier || usedByUser) {
                    throw new IllegalArgumentException("Phone number already used by another user or supplier");
                }
                supplier.setPhoneNumber(phoneNumber);
            }
        }

        supplier.setUpdatedAt(LocalDateTime.now());
        supplierRepository.save(supplier);
        return supplierMapper.toSupplierDto(supplier);
    }


    public void deleteSupplierById(Long id) {
        Optional<SupplierEntity> supplierEntityOptional = supplierRepository.findById(id);
        if (supplierEntityOptional.isEmpty()) throw new SupplierNotFoundException("Supplier with id = " + id + " not found");
        supplierRepository.deleteById(id);
    }

    public void validateSupplierUniqueness(String email, String phoneNumber) {
        if (supplierRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("supplier with email already exists");
        }

        if (supplierRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with phone number already exists");
        }
    }

    private String resolveEmailForSupplier(User user, String email) {

        if (email == null || email.isBlank() || email.equals(user.getEmail())) {
            return user.getEmail();
        }

        boolean emailUsedBySupplier = supplierRepository.existsByEmail(email);
        boolean emailUsedByUser = userService.checkEmailUniqueness(email);

        if (emailUsedBySupplier || emailUsedByUser) {
            throw new IllegalArgumentException("Email already used by another user or supplier");
        }

        return email;
    }
    private String resolvePhoneForSupplier(User user, String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isBlank()|| phoneNumber.equals(user.getPhoneNumber())) {
            return user.getPhoneNumber();
        }

        boolean phoneUsedBySupplier = supplierRepository.existsByPhoneNumber(phoneNumber);
        boolean phoneUsedByUser = userService.checkPhoneUniqueness(phoneNumber);

        if (phoneUsedBySupplier || phoneUsedByUser) {
            throw new IllegalArgumentException("Phone number already used by another user or supplier");
        }

        return phoneNumber;
    }

    public SupplierEntity getSupplierByUser(User user) {
        return supplierRepository.findByUserId(user.getId())
                .orElseThrow(() -> new SupplierNotFoundException(
                        "Supplier not found for user id: " + user.getId()
                ));
    }


    public SupplierEntity getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Supplier not found with id: " + supplierId));
    }

    public SupplierResponse getSupplier(Long supplierId) {
        return supplierMapper.toSupplierDto(getSupplierById(supplierId));
    }

    public Page<SupplierResponse> getAllUsers(Pageable pageable) {
        return supplierRepository.findAll(pageable)
                .map(supplierMapper::toSupplierDto);
    }
}
