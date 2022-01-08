package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;
import com.itechart.retailers.service.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itechart.retailers.controller.constant.Message.STATUSES_UPDATED_MSG;
import static com.itechart.retailers.controller.constant.Message.USER_CREATED_MSG;
import static com.itechart.retailers.security.constant.Authority.USER_GET_AUTHORITY;
import static com.itechart.retailers.security.constant.Authority.USER_POST_AUTHORITY;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    public static final String GET_USERS_MAPPING = "/users";
    public static final String POST_USERS_MAPPING = "/users";
    public static final String PUT_USERS_MAPPING = "/users/{id}";
    private final AdminService adminService;
    private final UserService userService;
    private static final String GET_AUTHORITIES = "hasAuthority('" + USER_GET_AUTHORITY + "')";
    private static final String POST_AUTHORITIES = "hasAuthority('" + USER_POST_AUTHORITY + "')";

    @GetMapping(GET_USERS_MAPPING)
    @PreAuthorize(GET_AUTHORITIES)
    public List<User> getUsers(@RequestParam(required = false) String role) throws RoleNotFoundException {
        return userService.getUsers(role);
    }

    @PostMapping(POST_USERS_MAPPING)
    @PreAuthorize(POST_AUTHORITIES)
    public ResponseEntity<?> createUser(@RequestBody User user) throws MailIsAlreadyInUse {
        adminService.createUser(user);
        return ResponseEntity.ok(new MessageResp(USER_CREATED_MSG));
    }

    @PutMapping(PUT_USERS_MAPPING)
    @PreAuthorize(POST_AUTHORITIES)
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        adminService.updateUserStatus(id, isActive);
        return ResponseEntity.ok(new MessageResp(STATUSES_UPDATED_MSG));
    }
}
