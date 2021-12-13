package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Item getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(@RequestBody Item item) {
        itemService.save(item);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            itemService.deleteById(id);
        }
    }
}