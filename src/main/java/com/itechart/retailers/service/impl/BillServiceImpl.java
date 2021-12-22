package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.repository.BillItemRepository;
import com.itechart.retailers.repository.BillRepository;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;
    private final BillItemRepository billItemRepo;
    private final ItemRepository itemRepo;
    private final LocationItemRepository locationItemRepo;

    @Override
    @Transactional
    public void createBill(Bill bill, Long locationId, Long shopManagerId) {
        bill.setRegDateTime(LocalDateTime.now());
        bill.setLocation(new Location(locationId));
        bill.setShopManager(new User(shopManagerId));

        Set<BillItem> billItems = bill.getItemAssoc();

        bill = billRepo.save(bill);

//        long totalAmount = 0;
//        long totalUnits = 0;

        for (BillItem billItem : billItems){
            Item item = itemRepo.findItemByUpc(billItem.getItem().getUpc()).get();

//            if(locationItemRepo.existsLocationItemByLocationIdAndItemId(locationId, item.getId())){
            if(locationItemRepo.getByLocationIdAndItemId(locationId, item.getId()).get().getAmount()
                    >= billItem.getAmount()){
                billItem.setItem(item);
                billItem.setBill(bill);

//                billItemRepo.save(billItem);
            } else {
                //TODO: error
            }
//            totalAmount += billItem.getAmount();
//            itemRepo.findItemByUpc(billItem.getItem().getUpc()).get().getUnits();
        }

        billItemRepo.saveAll(billItems);
    }
}
