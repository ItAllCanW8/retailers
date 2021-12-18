package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Item save(Item item);

    Item getById(Long itemId);

    void delete(Item item);

    void deleteById(Long id);

    List<Item> findItemsByCustomerId(Long customerId);

    Optional<Item> findItemByUpc(String upc);

}
