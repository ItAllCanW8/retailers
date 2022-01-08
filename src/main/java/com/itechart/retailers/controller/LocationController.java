package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.response.LocationPageResp;
import com.itechart.retailers.model.payload.response.LocationResp;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.LocationIdentifierAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.itechart.retailers.controller.constant.Message.LOCATION_ADDED_MSG;
import static com.itechart.retailers.controller.constant.Message.LOCATION_DELETED_MSG;
import static com.itechart.retailers.security.constant.Authority.ADMIN_ROLE;
import static com.itechart.retailers.security.constant.Authority.LOCATION_GET_AUTHORITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {
	public static final String GET_LOCATIONS_MAPPING = "/locations";
	public static final String POST_LOCATIONS_MAPPING = "/locations";
	public static final String GET_CURRENT_LOCATION_MAPPING = "/current-location";
	public static final String DELETE_LOCATION_MAPPING = "/locations/{id}";
	public static final String GET_LOCATION_AUTHORITY = "hasAuthority('" + LOCATION_GET_AUTHORITY + "')";
	private final String POST_LOCATION_AUTHORITY = "hasRole('" + ADMIN_ROLE + "')";

	private final AdminService adminService;
	private final SecurityContextService securityService;
	private final LocationService locationService;

    @GetMapping(GET_LOCATIONS_MAPPING)
    @PreAuthorize(GET_LOCATION_AUTHORITY)
    public LocationPageResp getLocations(
            @RequestParam(required = false) Boolean exceptCurrent,
            @RequestParam(required = false) Integer page
    ) {
        return locationService.getLocations(exceptCurrent, page);
    }

	@GetMapping(GET_CURRENT_LOCATION_MAPPING)
	@PreAuthorize(GET_LOCATION_AUTHORITY)
	public LocationResp getCurrentLocation() {
		return new LocationResp(securityService.getCurrentLocation(), locationService.getCurrentAvailableCapacity());
	}

	@PostMapping(POST_LOCATIONS_MAPPING)
	@PreAuthorize(POST_LOCATION_AUTHORITY)
	public ResponseEntity<?> createLocation(@RequestBody Location location) throws LocationIdentifierAlreadyExists {
		adminService.createLocation(location);
		return ResponseEntity.ok(new MessageResp(LOCATION_ADDED_MSG));
	}

	@DeleteMapping(DELETE_LOCATION_MAPPING)
	@PreAuthorize(POST_LOCATION_AUTHORITY)
	public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
		adminService.deleteLocation(id);
		return ResponseEntity.ok(new MessageResp(LOCATION_DELETED_MSG));
	}

	@DeleteMapping(POST_LOCATIONS_MAPPING)
	@PreAuthorize(POST_LOCATION_AUTHORITY)
	public ResponseEntity<?> deleteLocations(@RequestBody Set<Long> ids) {
		adminService.deleteLocations(ids);
		return ResponseEntity.ok(new MessageResp(LOCATION_DELETED_MSG));
	}
}
