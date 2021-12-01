package com.itechart.retailers.service.impl;


import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item getById(Long itemId) {
        return itemRepository.getById(itemId);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
