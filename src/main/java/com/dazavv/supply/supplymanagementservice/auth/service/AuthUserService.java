package com.dazavv.supply.supplymanagementservice.auth.service;

import com.dazavv.supply.supplymanagementservice.auth.entity.AuthUser;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.repository.AuthUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public AuthUser getByLogin(String login) {
        return authUserRepository.findByLogin(login)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with login: " + login));
    }

    public AuthUser getUserById(Long id) {
        return authUserRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + id));
    }

    public void checkExistedUserByEmailAndPhoneNumber(String email, String phoneNumber) {
        if (authUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }

        if (authUserRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with phone number already exists");
        }
    }
    public boolean checkExistedUserByEmail(String email) {
        if (authUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }
        return true;
    }

    public boolean checkExistedUserPhoneNumber(String phoneNumber) {
        if (authUserRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with phone number already exists");
        }
        return false;
    }

    public void checkExistedUserByLogin(String login) {
        if (authUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login" + login + "already exists");
        }
    }

    public void validateUserUniqueness(String login, String email, String phoneNumber) {
        checkExistedUserByLogin(login);
        checkExistedUserByEmailAndPhoneNumber(email, phoneNumber);
    }

    public void save(AuthUser user) {
        authUserRepository.save(user);
    }

    public void addNewRole(Long userId, Role role) {
        AuthUser user = getUserById(userId);

        user.getRoles().add(role);

        authUserRepository.save(user);
    }
}