package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.ItemDtoCreation;
import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.CategoryService;
import com.itechart.retailers.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ItemController {

	private final ItemService itemService;
	private final CategoryService categoryService;

	private final String authorities = "hasAuthority('ADMIN')";

	@GetMapping("/items")
	public List<Item> getAll() {
		return itemService.findAll();
	}

	@GetMapping("/items/{id}")
	@PreAuthorize(authorities)
	public Item getById(@PathVariable Long id) {
		return itemService.getById(id);
	}

	@PostMapping("/items")
	@PreAuthorize(authorities)
	public ResponseEntity<?> create(@RequestBody ItemDtoCreation itemDto) {
		System.out.println("im here w item" + itemDto);

		Category category = categoryService.saveIfNotExists(Category.builder().name(itemDto.getCategoryName()).build());
		Item item = Item.builder()
				.upc(itemDto.getUpc())
				.label(itemDto.getLabel())
				.units(itemDto.getUnits())
				.category(category)
				.build();

		itemService.save(item);
		return ResponseEntity.ok(new MessageResponse("Item added."));
	}

	@DeleteMapping("/items")
	@PreAuthorize(authorities)
	public void deleteById(@RequestBody Set<Long> ids) {
		for (Long id : ids) {
			itemService.deleteById(id);
		}
	}
}