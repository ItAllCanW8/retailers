package com.itechart.retailers.service.impl;


import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item save(Item item) {
		return itemRepository.save(item);
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

	@Override
	public List<Item> findItemsByCustomerId(Long customerId) {
		return itemRepository.findItemsByCustomerId(customerId);
	}
}
