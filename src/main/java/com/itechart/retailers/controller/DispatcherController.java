package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dispatcher")
public class DispatcherController {

	private final UserService userService;
	private final ApplicationService applicationService;

	private final String authorities = "hasAuthority('DISPATCHER')";

	private User dispatcher;

	@GetMapping("/application")
	@PreAuthorize(authorities)
	public List<Application> getAll() {
		if (dispatcher == null) {
			setDispatcher();
		}
		return applicationService.findApplicationsByDestLocation(dispatcher.getLocation());
	}

	@PostMapping("/application")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createApplication(@RequestBody ApplicationDto applicationDto) {



		return ResponseEntity.ok(new MessageResponse("Application created."));
	}


	private void setDispatcher() {
		String currentDispatcherEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		this.dispatcher = userService.getByEmail(currentDispatcherEmail).get();
	}

}
