package com.itechart.retailers.controller;

import com.itechart.retailers.model.Item;
import com.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin("*")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }
}