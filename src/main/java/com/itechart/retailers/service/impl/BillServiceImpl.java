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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;
    private final BillItemRepository billItemRepo;
    private final LocationItemRepository locationItemRepo;

    @Override
    @Transactional(rollbackFor = {ItemAmountException.class, UndefinedItemException.class})
    public Bill createBill(Bill bill, Long locationId, Long shopManagerId) throws ItemAmountException, UndefinedItemException {
        bill.setRegDateTime(LocalDateTime.now());
        bill.setLocation(new Location(locationId));
        bill.setShopManager(new User(shopManagerId));

        bill = billRepo.save(bill);

        List<BillItem> billItems = bill.getItemAssoc();
        List<String> itemUpcs = new ArrayList<>();

        billItems.forEach(billItem -> itemUpcs.add(billItem.getItem().getUpc()));

        List<LocationItem> locationItems = locationItemRepo.findAllByLocationIdAndItemUpc(locationId, itemUpcs);

        for (BillItem billItem : billItems){
            String itemUpc = billItem.getItem().getUpc();

            LocationItem locationItem = locationItems.stream()
                    .filter(li -> itemUpc.equals(li.getItem().getUpc()))
                    .findAny()
                    .orElseThrow(() -> new UndefinedItemException("There is no item with upc " + itemUpc + " in shop!"));

            int locationItemAmount = locationItem.getAmount();
            int billItemAmount = billItem.getAmount();

            if(locationItemAmount < billItemAmount){
                throw new ItemAmountException("Item amount to sell cannot be greater than actual amount in shop!");
            }

            locationItem.setAmount(locationItemAmount - billItemAmount);
            locationItemRepo.save(locationItem);

            billItem.setPrice(locationItem.getPrice());
            billItem.setBill(bill);
            billItem.setItem(new Item(locationItem.getItem().getId()));
        }

        billItemRepo.saveAll(billItems);

        return bill;
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
