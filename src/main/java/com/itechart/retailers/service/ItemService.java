package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.ItemPageResp;
import com.itechart.retailers.service.impl.ItemAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Item save(Item item) throws ItemAlreadyExistsException;

    Item getById(Long itemId);

    void delete(Item item);

    void deleteById(Long id);

    ItemPageResp findItemsByCustomerId(Integer page);

    Optional<Item> findItemByUpc(String upc);

    Item create(Item item) throws ItemAlreadyExistsException;

}
