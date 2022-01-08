package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.TaxService;
import com.itechart.retailers.service.exception.IncorrectTaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itechart.retailers.controller.constant.Message.NEGATIVE_TAX_MSG;
import static com.itechart.retailers.controller.constant.Message.TAXES_UPDATED_MSG;
import static com.itechart.retailers.security.constant.Authority.DIRECTOR_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaxController {

	public static final String PUT_RENTAL_TAXES_MAPPING = "/taxes/rental";
	private static final String AUTHORITIES = "hasRole('" + DIRECTOR_ROLE + "')";
	public static final String PUT_CATEGORY_TAXES_MAPPING = "/taxes/category";

	private final TaxService taxService;

	@PutMapping(PUT_RENTAL_TAXES_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> updRentalTax(@RequestBody List<Location> locations) {
		try {
			taxService.updateRentalTax(locations);
			return ResponseEntity.ok(new MessageResp(TAXES_UPDATED_MSG));
		} catch (IncorrectTaxException incorrectTaxException) {
			return ResponseEntity.badRequest().body(new MessageResp(NEGATIVE_TAX_MSG));
		}

	}

	@PutMapping(PUT_CATEGORY_TAXES_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> updCategoryTax(@RequestBody List<CustomerCategory> categoryTaxes) throws IncorrectTaxException {
		taxService.updateItemCategoryTaxes(categoryTaxes);
		return ResponseEntity.ok(new MessageResp(TAXES_UPDATED_MSG));
	}
}
