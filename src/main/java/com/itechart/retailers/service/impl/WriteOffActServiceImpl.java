package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.entity.LocationItem;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.model.entity.WrittenOffItem;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.repository.WriteOffActRepository;
import com.itechart.retailers.repository.WrittenOffItemRepository;
import com.itechart.retailers.service.WriteOffActService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WriteOffActServiceImpl implements WriteOffActService {

    private final WriteOffActRepository writeOffActRepo;
    private final WrittenOffItemRepository writeOffItemRepo;
    private final LocationItemRepository locationItemRepo;
    private final ItemRepository itemRepo;

    @Override
    public List<WriteOffAct> findAll() {
        return null;
    }

    @Transactional
    @Override
    public boolean save(WriteOffAct writeOffAct, Long locationId) {
        Set<WrittenOffItem> writtenOffItems = writeOffAct.getWrittenOffItems();

        System.out.println(writeOffAct);
        System.out.println(writtenOffItems);

        long totalAmount = 0L;
        long totalSum = 0L;

        for (WrittenOffItem writtenOffItem : writtenOffItems){
            Integer writtenOffAmount = writtenOffItem.getAmount();
            Long itemId = itemRepo.findItemByUpc(writtenOffItem.getItem().getUpc()).get().getId();

            System.out.println(itemId);

            LocationItem storedItem = locationItemRepo.getByLocation_IdAndItem_Id(locationId, itemId);

            int storedAmount = storedItem.getAmount();

            if(storedAmount >= writtenOffAmount){
                locationItemRepo.updateAmountById(storedAmount - writtenOffAmount, storedItem.getId());
                writtenOffItem.setWriteOffAct(writeOffAct);
                writtenOffItem.setItem(new Item(itemId));
                writeOffItemRepo.save(writtenOffItem);
            }
//            else {
//                TODO error
//            }

            totalAmount += writtenOffAmount;
            totalSum += storedItem.getCost();
        }

        writeOffAct.setTotalAmount(totalAmount);
        writeOffAct.setTotalSum(totalSum);

        return writeOffActRepo.save(writeOffAct).getId() != null;
    }

    @Override
    public WriteOffAct getById(Long id) {
        return null;
    }

    @Override
    public void delete(WriteOffAct user) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
