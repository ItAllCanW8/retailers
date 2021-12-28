package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.entity.projection.BillView;
import com.itechart.retailers.model.entity.projection.WriteOffActView;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.repository.WriteOffActRepository;
import com.itechart.retailers.repository.WrittenOffItemRepository;
import com.itechart.retailers.service.WriteOffActService;
import com.itechart.retailers.service.exception.ItemAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Transactional(rollbackFor = ItemAmountException.class)
    @Override
    public WriteOffAct save(WriteOffAct writeOffAct, Long locationId) throws ItemAmountException {
        writeOffAct.setLocation(new Location(locationId));
        writeOffAct = writeOffActRepo.save(writeOffAct);

        Set<WrittenOffItem> writtenOffItems = writeOffAct.getWrittenOffItems();
//        List<Long> ids = entities.stream()
//                .map(Entity::getId).collect(Collectors.toList());

//        Set<Long> writtenOffItemIds = writtenOffItems.stream().map(WrittenOffItem::getId).collect(Collectors.toSet());

        Set<String> itemUpcs = new HashSet<>();
        List<Integer> writtenOffItemAmounts = new ArrayList<>();

        for (WrittenOffItem writtenOffItem : writtenOffItems) {
            itemUpcs.add(writtenOffItem.getItem().getUpc());
            writtenOffItemAmounts.add(writtenOffItem.getAmount());
            writtenOffItem.setWriteOffAct(writeOffAct);
        }

        List<Long> itemIds = itemRepo.findItemIdsByUpc(itemUpcs);

        writtenOffItems.forEach(writtenOffItem -> writtenOffItem.setItem(new Item(itemIds)));

//        List<LocationItem> storedItems = locationItemRepo.findAllByLocationIdAndItemId(locationId, itemIds);
        List<Integer> storedItemAmounts = locationItemRepo.loadStoredItemAmounts(locationId, itemIds);

        int counter = 0;

        for (int i = 0; i < writtenOffItems.size(); i++){
            int storedAmount = storedItemAmounts.get(i);
            int writtenOffAmount = writtenOffItemAmounts.get(i);

            if(storedAmount < writtenOffAmount){
                throw new ItemAmountException("Item amount to write-off cannot be greater than actual amount in shop");
            }

            locationItemRepo.updateItemAmount(locationId, itemIds.get(i), storedAmount - writtenOffAmount);

            writtenOffItem.setWriteOffAct(writeOffAct);
            writtenOffItem.setItem(new Item(itemId));
        }

//        for (WrittenOffItem writtenOffItem : writtenOffItems) {
//            Integer writtenOffAmount = writtenOffItem.getAmount();
////            Long itemId = itemRepo.findItemByUpc(writtenOffItem.getItem().getUpc()).get().getId();
//
////            LocationItem storedItem = locationItemRepo.getByLocationIdAndItemId(locationId, itemId).get();
//
//            int storedAmount = storedItem.getAmount();
//
//            if (storedAmount < writtenOffAmount) {
//                throw new ItemAmountException("Item amount to write-off cannot be greater than actual amount in shop");
//            }
//
//            locationItemRepo.updateItemAmount(locationId, itemId, storedAmount - writtenOffAmount);
//
//            writtenOffItem.setWriteOffAct(writeOffAct);
//            writtenOffItem.setItem(new Item(itemId));
////            writeOffItemRepo.save(writtenOffItem);
//        }

        writeOffItemRepo.saveAll(writtenOffItems);

        return writeOffAct;
    }

    @Override
    public List<WriteOffActDto> loadWriteOffActs(Long locationId) {
        List<WriteOffActView> writeOffActViews = writeOffActRepo.findAllByLocationId(locationId);
        List<WriteOffActDto> writeOffActDtos = new ArrayList<>(writeOffActViews.size());

        for (WriteOffActView writeOffActView : writeOffActViews) {
            Set<WrittenOffItem> writtenOffItems = writeOffActView.getWrittenOffItems();
            Set<Long> itemIds = new HashSet<>(writtenOffItems.size());

            long totalItemAmount = 0;

            for (WrittenOffItem writtenOffItem : writtenOffItems) {
                totalItemAmount += writtenOffItem.getAmount();

                itemIds.add(writtenOffItem.getItem().getId());
            }

            float totalItemSum = locationItemRepo.loadItemCostSum(locationId, itemIds);

            writeOffActDtos.add(WriteOffActDto.builder()
                    .identifier(writeOffActView.getIdentifier())
                    .dateTime(writeOffActView.getDateTime())
                    .totalItemAmount(totalItemAmount)
                    .totalItemSum(totalItemSum)
                    .build());
        }

        return writeOffActDtos;
    }
}
