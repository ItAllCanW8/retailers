package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itechart.retailers.controller.constant.Message.*;
import static com.itechart.retailers.security.constant.Authority.APPLICATION_GET_AUTHORITY;
import static com.itechart.retailers.security.constant.Authority.APPLICATION_POST_AUTHORITY;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {
	public static final String GET_APPLICATIONS_MAPPING = "/applications";
	public static final String POST_APPLICATIONS_MAPPING = "/applications";
	public static final String GET_APPLICATION_BY_ID_MAPPING = "/application/{id}";
	public static final String POST_DISPATCH_ITEMS_MAPPING = "/dispatch-items";
	public static final String PUT_ACCEPT_APPLICATION_MAPPING = "/application/{id}/accept";
	public static final String PUT_FORWARD_APPLICATION_MAPPING = "/application/{id}/forward";
	public static final String DELETE_APPLICATION_MAPPING = "/{id}";
	private static final String AUTHORITIES = "hasAuthority('" + APPLICATION_GET_AUTHORITY + "') "
			+ "or hasAuthority('" + APPLICATION_POST_AUTHORITY + "')";

	private final ApplicationService applicationService;
	private final LocationService locationService;

	@GetMapping(GET_APPLICATIONS_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ApplicationPageResp getCurrentApplications(@RequestParam(required = false) Integer page) {
		return applicationService.getCurrentApplications(page);
	}

	@GetMapping(GET_APPLICATION_BY_ID_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public Application getById(@PathVariable Long id) {
		return applicationService.getById(id);
	}

	@PostMapping(POST_APPLICATIONS_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> create(@RequestBody ApplicationReq applicationReq) throws ItemNotFoundException, ApplicationAlreadyExists {
		applicationService.save(applicationReq);
		return ResponseEntity.ok(new MessageResp(APPLICATION_CREATED_MSG));
	}

	@PostMapping(POST_DISPATCH_ITEMS_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> dispatchItems(@RequestBody DispatchItemReq dispatchItemReq) throws DispatchItemException, ItemAmountException, LocationNotFoundException, ItemNotFoundException, ApplicationAlreadyExists {
		applicationService.dispatchItems(dispatchItemReq);
		return ResponseEntity.ok(new MessageResp(SUCCESS_DISPATCH_MSG));
	}

	@PutMapping(PUT_ACCEPT_APPLICATION_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> acceptApplication(@PathVariable Long id)
			throws CustomerCategoryNotFoundException, ApplicationNotFoundException, ItemAmountException, TaxesNotDefinedException {
		locationService.acceptApplication(id);
		return ResponseEntity.ok(new MessageResp(APPLICATION_ACCEPTED_MSG));
	}

	@PutMapping(PUT_FORWARD_APPLICATION_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> forwardApplication(@PathVariable Long id, @RequestBody String locationIdentifier)
			throws LocationNotFoundException {
		applicationService.forwardApplication(id, locationIdentifier);
		return ResponseEntity.ok(new MessageResp(SUCCESS_FORWARD_MSG));
	}

	@DeleteMapping(DELETE_APPLICATION_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public void deleteById(@PathVariable Long id) {
		applicationService.deleteById(id);
	}
}
