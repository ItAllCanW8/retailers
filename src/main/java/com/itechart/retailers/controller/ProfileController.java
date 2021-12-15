package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.ProfileReq;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.EmptyPasswordException;
import com.itechart.retailers.service.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public User getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getByEmail(email).get();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileReq profileReq) {
        try {
            userService.update(profileReq.getUser(), profileReq.getCurrentPassword(), profileReq.getNewPassword());
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect current password!"));
        } catch (EmptyPasswordException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("New password cannot be empty!"));
        }
        return ResponseEntity.ok(new MessageResponse("Profile updated."));
    }
}
