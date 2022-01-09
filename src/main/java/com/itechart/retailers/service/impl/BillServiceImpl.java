package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.entity.projection.BillView;
import com.itechart.retailers.repository.BillItemRepository;
import com.itechart.retailers.repository.BillRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.service.BillService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.itechart.retailers.controller.constant.Message.BILL_ITEM_AMOUNT_EXCEPTION_MSG;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;
    private final BillItemRepository billItemRepo;
    private final LocationItemRepository locationItemRepo;

    @Override
    @Transactional(rollbackFor = {ItemAmountException.class, ItemNotFoundException.class})
    public Bill createBill(Bill bill, Long locationId, Long shopManagerId)
            throws ItemAmountException, ItemNotFoundException {

        bill.setRegDateTime(LocalDateTime.now());
        bill.setLocation(new Location(locationId));
        bill.setShopManager(new User(shopManagerId));
        bill = billRepo.save(bill);

        List<BillItem> billItems = bill.getItemAssoc();
        List<String> itemUpcs = new ArrayList<>();
        billItems.forEach(billItem -> itemUpcs.add(billItem.getItem().getUpc()));

        List<LocationItem> locationItems = locationItemRepo.findAllByLocationIdAndItemUpc(locationId, itemUpcs);
        createBillItems(bill, billItems, locationItems);
        billItemRepo.saveAll(billItems);
        return bill;
    }

    private void createBillItems(Bill bill, List<BillItem> billItems, List<LocationItem> locationItems)
            throws ItemNotFoundException, ItemAmountException {
        for (BillItem billItem : billItems) {
            String itemUpc = billItem.getItem().getUpc();
            LocationItem locationItem = locationItems.stream()
                    .filter(li -> itemUpc.equals(li.getItem().getUpc()))
                    .findAny()
                    .orElseThrow(ItemNotFoundException::new);
            int locationItemAmount = locationItem.getAmount();
            int billItemAmount = billItem.getAmount();
            if (locationItemAmount < billItemAmount) {
                throw new ItemAmountException(BILL_ITEM_AMOUNT_EXCEPTION_MSG);
            }
            locationItem.setAmount(locationItemAmount - billItemAmount);
            locationItemRepo.save(locationItem);
            billItem.setPrice(locationItem.getPrice());
            billItem.setBill(bill);
            billItem.setItem(new Item(locationItem.getItem().getId()));
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
                int itemAmount = item.getAmount();
                totalItemAmount += itemAmount;
                totalItemSum += item.getPrice() * itemAmount;
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
