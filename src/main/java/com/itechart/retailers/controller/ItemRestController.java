package com.itechart.retailers.controller;

import com.itechart.retailers.model.Item_TEST;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin("*")
public class ItemRestController {
    private List<Item_TEST> items = List.of(
            new Item_TEST(1L, "Manka"),
            new Item_TEST(2L, "Grechka"),
            new Item_TEST(3L, "Perlovka")
    );

    @GetMapping
    public List<Item_TEST> getAll() {
        return items;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('item:read')")
    public Item_TEST getById(@PathVariable Long id) {
        return items.stream().filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('item:write')")
    public Item_TEST create(@RequestBody Item_TEST item) {
        this.items.add(item);
        return item;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('item:write')")
    public void deleteById(@PathVariable Long id) {
        this.items.removeIf(item -> item.getId().equals(id));
    }
}
