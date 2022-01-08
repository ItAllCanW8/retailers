package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.ProfileReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.EmptyPasswordException;
import com.itechart.retailers.service.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.itechart.retailers.controller.constant.Message.*;
import static com.itechart.retailers.security.constant.Authority.DEFAULT_AUTHORITY;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

	public static final String AUTHORITIES = "hasAuthority('" + DEFAULT_AUTHORITY + "')";

	private final UserService userService;

	@GetMapping("/profile")
	@PreAuthorize(AUTHORITIES)
	public User getProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.getByEmail(email).get();
	}

	@PutMapping("/profile")
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> updateProfile(@RequestBody ProfileReq profileReq) throws EmptyPasswordException, IncorrectPasswordException {
		userService.update(profileReq.getUser(), profileReq.getCurrentPassword(), profileReq.getNewPassword());
		return ResponseEntity.ok(new MessageResp(PROFILE_UPDATED_MSG));
	}
}
