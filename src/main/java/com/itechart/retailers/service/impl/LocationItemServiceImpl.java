package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.LocationItem;
import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.LocationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationItemServiceImpl implements LocationItemService {

	private final SecurityContextService securityService;
	private final LocationItemRepository locationItemRepository;

	@Override
	public List<LocationItemResp> getCurrentLocationItems(/*Boolean isAllItemsShown*/) {
		List<LocationItem> locationItems = locationItemRepository.findByLocation(securityService.getCurrentLocation());
		return locationItems.stream()
				.map(this::getLocationItemResp)
				.filter(item -> item.getAmount() != 0)
				.toList();
	}

	private LocationItemResp getLocationItemResp(LocationItem li) {
		return LocationItemResp.builder()
				.upc(li.getItem().getUpc())
				.label(li.getItem().getLabel())
				.amount(li.getAmount())
				.cost(li.getCost())
				.price(li.getPrice())
				.build();
	}
}
