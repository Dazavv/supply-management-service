package com.dazavv.supply.supplymanagementservice.auth.service;

import com.dazavv.supply.supplymanagementservice.auth.dto.response.UserResponse;
import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import com.dazavv.supply.supplymanagementservice.auth.enums.Role;
import com.dazavv.supply.supplymanagementservice.auth.mapper.UserMapper;
import com.dazavv.supply.supplymanagementservice.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with login: " + login));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + id));
    }

    public void checkExistedUserByEmailAndPhoneNumber(String email, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with phone number already exists");
        }
    }
    public boolean checkExistedUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }
        return true;
    }

    public boolean checkExistedUserPhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with phone number already exists");
        }
        return false;
    }

    public void checkExistedUserByLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login" + login + "already exists");
        }
    }

    public void validateUserUniqueness(String login, String email, String phoneNumber) {
        checkExistedUserByLogin(login);
        checkExistedUserByEmailAndPhoneNumber(email, phoneNumber);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void addNewRole(Long userId, Role role) {
        User user = getUserById(userId);

        user.getRoles().add(role);

        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(getUserById(id));
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserDto);
    }
}