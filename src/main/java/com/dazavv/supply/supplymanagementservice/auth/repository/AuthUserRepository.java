package com.dazavv.supply.supplymanagementservice.auth.repository;

import com.dazavv.supply.supplymanagementservice.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByLogin(String login);
    boolean existsByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}