package com.itechart.retailers.service.impl;


import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.payload.response.ItemPageResp;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.CategoryService;
import com.itechart.retailers.service.ItemService;
import com.itechart.retailers.service.exception.ItemAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;
	private final CategoryService categoryService;
	private final SecurityContextService securityService;

	@Value("${pagination.pageSize}")
	private Integer pageSize;

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item save(Item item) throws ItemAlreadyExistsException {
		Optional<Item> optionalItem = itemRepository.findItemByUpc(item.getUpc());
		if (optionalItem.isPresent()) {
			throw new ItemAlreadyExistsException("Item already exists");
		}
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
    public ItemPageResp findItemsByCustomerId(Integer page) {
        Page<Item> items = itemRepository.findItemsByCustomerId(
                securityService.getCurrentCustomerId(), PageRequest.of(page, pageSize)
        );
        return new ItemPageResp(items.getContent(), items.getTotalPages());
    }

	@Override
	public Optional<Item> findItemByUpc(String upc) {
		return itemRepository.findItemByUpc(upc);
	}

	@Override
	public Item create(Item item) throws ItemAlreadyExistsException {
		Long customerId = securityService.getCurrentCustomerId();
		Category category = categoryService.saveIfNotExists(
				item.getCategory(), customerId);
		Customer customer = new Customer();
		customer.setId(customerId);
		item.setCustomer(customer);
		item.setCategory(category);
		return save(item);
	}
}
