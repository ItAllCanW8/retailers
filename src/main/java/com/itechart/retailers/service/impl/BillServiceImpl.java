package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.entity.projection.BillView;
import com.itechart.retailers.repository.BillItemRepository;
import com.itechart.retailers.repository.BillRepository;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.service.BillService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

	private final BillRepository billRepo;
	private final BillItemRepository billItemRepo;
	private final ItemRepository itemRepo;
	private final LocationItemRepository locationItemRepo;

	@Override
	@Transactional(rollbackFor = ItemAmountException.class)
	public void createBill(Bill bill, Long locationId, Long shopManagerId) throws ItemAmountException, UndefinedItemException {
		bill.setRegDateTime(LocalDateTime.now());
		bill.setLocation(new Location(locationId));
		bill.setShopManager(new User(shopManagerId));

		List<BillItem> billItems = bill.getItemAssoc();

		bill = billRepo.save(bill);

		for (BillItem billItem : billItems) {
			Optional<Item> optionalItem = itemRepo.findItemByUpc(billItem.getItem().getUpc());
			if (optionalItem.isPresent()) {
				Item item = optionalItem.get();
				Long itemId = item.getId();

				LocationItem locationItem = locationItemRepo.getByLocationIdAndItemId(locationId, itemId).get();

				int locationItemAmount = locationItem.getAmount();
				int billItemAmount = billItem.getAmount();

				if (locationItemAmount < billItemAmount) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					throw new ItemAmountException("Item amount to sell cannot be more than actual amount in shop");
				}

				billItem.setPrice(locationItem.getPrice());
				billItem.setItem(item);
				billItem.setBill(bill);
				billItemRepo.save(billItem);
				locationItem.setAmount(locationItemAmount - billItemAmount);
				locationItemRepo.save(locationItem);
			} else {
				throw new UndefinedItemException();
			}
		}
	}

	@Override
	public List<BillDto> loadShopBills(Long shopId) {
		List<BillView> billViews = billRepo.findAllByLocationId(shopId);
		List<BillDto> billDtos = new ArrayList<>(billViews.size());

		for (BillView billView : billViews) {
			long totalItemAmount = 0;
			float totalItemSum = 0;

			for (BillItem item : billView.getItemAssoc()) {
				totalItemAmount += item.getAmount();
				totalItemSum += item.getPrice();
			}

			billDtos.add(BillDto.builder()
					.number(billView.getNumber())
					.regDateTime(billView.getRegDateTime())
					.totalItemAmount(totalItemAmount)
					.totalItemSum(totalItemSum)
					.build());
		}

		return billDtos;
	}
}
