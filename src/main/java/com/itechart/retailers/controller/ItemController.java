package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.MessageResp;
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

    private final String authorities = "hasAuthority('item:get') or hasAuthority('item:post')";
    private Long customerId;

    @GetMapping("/items")
    public List<Item> getAll() {
        if (customerId == null) {
            setCustomerId();
        }

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
        if (customerId == null) {
            setCustomerId();
        }
        Category category = categoryService.saveIfNotExists(
                item.getCategory());
        Customer customer = new Customer();
        customer.setId(customerId);
        item.setCustomer(customer);
        item.setCategory(category);
        itemService.save(item);
        return ResponseEntity.ok(new MessageResp("Item added."));
    }

    @DeleteMapping("/items")
    @PreAuthorize(authorities)
    public void deleteById(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            itemService.deleteById(id);
        }
    }

    private void setCustomerId() {
        String currentCustomerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.customerId = userService.getByEmail(currentCustomerEmail).get().getCustomer().getId();
    }
}