package com.dazavv.supply.supplymanagementservice.auth.repository;

import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}