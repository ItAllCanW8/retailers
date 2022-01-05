package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.exception.UndefinedLocationException;
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
    public List<Application> getCurrentApplications() {
        return applicationService.getCurrentApplications();
    }

    @GetMapping(GET_APPLICATION_BY_ID_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public Application getById(@PathVariable Long id) {
        return applicationService.getById(id);
    }

    @PostMapping(POST_APPLICATIONS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> create(@RequestBody ApplicationReq applicationReq) throws UndefinedItemException {
        try {
            applicationService.save(applicationReq);
        } catch (UndefinedItemException e) {
            return ResponseEntity.badRequest().body(new MessageResp(ITEM_NOT_FOUND_MSG));
        }
        return ResponseEntity.ok(new MessageResp(APPLICATION_CREATED_MSG));
    }

    @PostMapping(POST_DISPATCH_ITEMS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> dispatchItems(@RequestBody DispatchItemReq dispatchItemReq) {
        try {
            applicationService.dispatchItems(dispatchItemReq);
        } catch (ItemAmountException e) {
            return ResponseEntity.badRequest().body(new MessageResp(INCORRECT_ITEM_AMOUNT_INPUT_MSG));
        }
        return ResponseEntity.ok(new MessageResp(SUCCESS_DISPATCH_MSG));
    }

    @PutMapping(PUT_ACCEPT_APPLICATION_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> acceptApplication(@PathVariable Long id) {
        try {
            locationService.acceptApplication(id);
        } catch (TaxesNotDefinedException e) {
            return ResponseEntity.badRequest().body(new MessageResp(TAXES_NOT_DEFINED_MSG));
        } catch (ItemAmountException e) {
            return ResponseEntity.badRequest().body(new MessageResp(NO_SPACE_IN_LOCATION_MSG));
        }
        return ResponseEntity.ok(new MessageResp(APPLICATION_ACCEPTED_MSG));
    }

    @PutMapping(PUT_FORWARD_APPLICATION_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> forwardApplication(@PathVariable Long id, @RequestBody String locationIdentifier) {
        try {
            applicationService.forwardApplication(id, locationIdentifier);
        } catch (UndefinedLocationException e) {
            return ResponseEntity.badRequest().body(new MessageResp(LOCATION_IS_NOT_FOUND_MSG));
        }
        return ResponseEntity.ok(new MessageResp(SUCCESS_FORWARD_MSG));
    }

    @DeleteMapping(DELETE_APPLICATION_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public void deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
    }

}
