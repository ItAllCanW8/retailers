package com.itechart.retailers.controller;

//import com.itechart.retailers.model.Item;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/items")
//@CrossOrigin("*")
//public class ItemRestController {
//    private List<Item> items = List.of(
//            new Item(1L, "Manka"),
//            new Item(2L, "Grechka"),
//            new Item(3L, "Perlovka")
//    );
//
//    @GetMapping
//    public List<Item> getAll() {
//        return items;
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('item:read')")
//    public Item getById(@PathVariable Long id) {
//        return items.stream().filter(item -> item.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('item:write')")
//    public Item create(@RequestBody Item item) {
//        this.items.add(item);
//        return item;
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('item:write')")
//    public void deleteById(@PathVariable Long id) {
//        this.items.removeIf(item -> item.getId().equals(id));
//    }
//}
