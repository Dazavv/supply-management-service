package com.dazavv.supply.supplymanagementservice.auth.service;

import com.dazavv.supply.supplymanagementservice.auth.dto.response.UserResponse;
import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.exception.RoleAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserAlreadyExistsException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserLinkedToSupplierException;
import com.dazavv.supply.supplymanagementservice.auth.exception.UserNotFoundException;
import com.dazavv.supply.supplymanagementservice.auth.mapper.UserMapper;
import com.dazavv.supply.supplymanagementservice.auth.repository.UserRepository;
import com.dazavv.supply.supplymanagementservice.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final UserMapper userMapper;

    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with login: " + login));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));
    }

    public boolean checkEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email already exists");
        }
        return true;
    }

    public boolean checkPhoneUniqueness(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("User with phone number already exists");
        }
        return false;
    }

    public void checkExistedUserByLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new UserAlreadyExistsException("User with login" + login + "already exists");
        }
    }

    public void validateUserUniqueness(String login, String email, String phoneNumber) {
        checkExistedUserByLogin(login);
        checkEmailUniqueness(email);
        checkPhoneUniqueness(phoneNumber);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void addNewRole(Long userId, Role role) {
        User user = getUserById(userId);

        if (user.getRoles().contains(role)) {
            throw new RoleAlreadyExistsException("Role already exists");
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (supplierRepository.existsByUserId(id)) {
            throw new UserLinkedToSupplierException("Cannot delete user, he is linked to suppliers");
        }

        userRepository.delete(getUserById(id));
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserDto);
    }
}