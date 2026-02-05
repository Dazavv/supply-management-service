package com.dazavv.supply.supplymanagementservice.auth.controller;

import com.dazavv.supply.supplymanagementservice.auth.dto.request.AddRoleToUserRequest;
import com.dazavv.supply.supplymanagementservice.auth.dto.response.UserResponse;
import com.dazavv.supply.supplymanagementservice.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@Valid @RequestBody AddRoleToUserRequest request) {
        userService.addNewRole(request.id(), request.role());
        return ResponseEntity.ok("User with id=: " + request.id() + " has new role: " + request.role());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserResponse> users = userService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
