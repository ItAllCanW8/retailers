package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.entity.projection.UserView;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final UserService userService;
    private final String roles = "hasRole('ADMIN')";

    @GetMapping("/users")
    @PreAuthorize(roles)
    public List<UserView> getUsers() {
        return adminService.findEmployees();
    }

    @GetMapping("/managers")
    @PreAuthorize("hasRole('DISPATCHER')")
    public List<User> getManagers() {
        return userService.getUsersByRoleNameInCurrentCustomer("WAREHOUSE_MANAGER");
    }

    @PostMapping("/users")
    @PreAuthorize(roles)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode("1111"));
        user.setActive(true);
        adminService.createUser(user);
        return ResponseEntity.ok(new MessageResp("User created."));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize(roles)
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        adminService.updateUserStatus(id, isActive);
        return ResponseEntity.ok(new MessageResp("Statuses updated."));
    }
}
