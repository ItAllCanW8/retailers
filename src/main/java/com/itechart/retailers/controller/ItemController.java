package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.CategoryService;
import com.itechart.retailers.service.ItemService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

	private final ItemService itemService;
	private final CategoryService categoryService;
	private final UserService userService;
	private final SecurityContextService securityContextService;

	private final String authorities = "hasAuthority('item:get') or hasAuthority('item:post')";
	private Long customerId;

	@GetMapping("/items")
	public List<Item> getAll() {
		customerId = securityContextService.getCurrentCustomerId();
		return itemService.findItemsByCustomerId(customerId);
	}


	@GetMapping("/items/{id}")
	@PreAuthorize(authorities)
	public Item getById(@PathVariable Long id) {
		return itemService.getById(id);
	}

	@PostMapping("/items")
	@PreAuthorize(authorities)
	public ResponseEntity<?> create(@RequestBody Item item) {
		customerId = securityContextService.getCurrentCustomerId();
		itemService.create(item, customerId);
		return ResponseEntity.ok(new MessageResp("Item added."));
	}

	@DeleteMapping("/items")
	@PreAuthorize(authorities)
	public void deleteById(@RequestBody Set<Long> ids) {
		for (Long id : ids) {
			itemService.deleteById(id);
		}
	}
}