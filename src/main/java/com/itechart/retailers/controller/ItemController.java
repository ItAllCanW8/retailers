package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.ItemPageResp;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.ItemService;
import com.itechart.retailers.service.exception.ItemAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.itechart.retailers.controller.constant.Message.ITEM_ADDED_MSG;
import static com.itechart.retailers.security.constant.Authority.ITEM_GET_AUTHORITY;
import static com.itechart.retailers.security.constant.Authority.ITEM_POST_AUTHORITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    public static final String GET_ITEMS_MAPPING = "/items";
    public static final String POST_ITEMS_MAPPING = "/items";
    public static final String DELETE_ITEMS_MAPPING = "/items";
    public static final String GET_ITEM_BY_ID_MAPPING = "/items/{id}";
    private static final String AUTHORITIES = "hasAuthority('" + ITEM_GET_AUTHORITY + "') "
            + "or hasAuthority('" + ITEM_POST_AUTHORITY + "')";

    private final ItemService itemService;

    @GetMapping(GET_ITEMS_MAPPING)
    public ItemPageResp getAll(@RequestParam(required = false) Integer page) {
        return itemService.findItemsByCustomerId(page);
    }

    @GetMapping(GET_ITEM_BY_ID_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public Item getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @PostMapping(POST_ITEMS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> create(@RequestBody Item item) throws ItemAlreadyExistsException {
        itemService.create(item);
        return ResponseEntity.ok(new MessageResp(ITEM_ADDED_MSG));
    }

    @DeleteMapping(DELETE_ITEMS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public void deleteById(@RequestBody Set<Long> ids) {
        ids.forEach(itemService::deleteById);
    }
}