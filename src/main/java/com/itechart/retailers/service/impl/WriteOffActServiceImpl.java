package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.entity.projection.WriteOffActView;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.repository.WriteOffActRepository;
import com.itechart.retailers.repository.WrittenOffItemRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.WriteOffActService;
import com.itechart.retailers.service.exception.ItemAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WriteOffActServiceImpl implements WriteOffActService {

    private final WriteOffActRepository writeOffActRepo;
    private final WrittenOffItemRepository writeOffItemRepo;
    private final LocationItemRepository locationItemRepo;
    private final SecurityContextService securityService;

    @Override
    public List<WriteOffActDto> loadCustomerWriteOffActs() {
        Long currentCustomerId = securityService.getCurrentCustomerId();
        List<WriteOffActView> writeOffActViews = writeOffActRepo.findAllByCustomerId(currentCustomerId);
        return convertViewsToDtos(writeOffActViews);
    }

    @Transactional(rollbackFor = ItemAmountException.class)
    @Override
    public WriteOffAct save(WriteOffAct writeOffAct) throws ItemAmountException {
        Long locationId = securityService.getCurrentLocationId();
        writeOffAct.setDateTime(LocalDateTime.now());
        writeOffAct.setLocation(new Location(locationId));
        writeOffAct = writeOffActRepo.save(writeOffAct);

        Set<WrittenOffItem> writtenOffItems = writeOffAct.getWrittenOffItems();
        List<String> itemUpcs = new ArrayList<>();

        for (WrittenOffItem writtenOffItem : writtenOffItems) {
            itemUpcs.add(writtenOffItem.getItem().getUpc());
        }

        List<LocationItem> locationItems = locationItemRepo.findAllByLocationIdAndItemUpc(locationId, itemUpcs);

        for (WrittenOffItem writtenOffItem : writtenOffItems) {
            String itemUpc = writtenOffItem.getItem().getUpc();

            LocationItem locationItem = locationItems.stream()
                    .filter(li -> itemUpc.equals(li.getItem().getUpc()))
                    .findAny()
                    .orElse(null);

            int storedAmount = locationItem.getAmount();
            int writtenOffAmount = writtenOffItem.getAmount();

            if (storedAmount < writtenOffAmount) {
                throw new ItemAmountException("Item amount to write-off cannot be greater than actual amount in shop");
            }

            Long itemId = locationItem.getItem().getId();

            locationItemRepo.updateItemAmount(locationId, itemId,
                    storedAmount - writtenOffAmount);

            writtenOffItem.setWriteOffAct(writeOffAct);
            writtenOffItem.setItem(new Item(itemId));
        }
        writeOffItemRepo.saveAll(writtenOffItems);

        return writeOffAct;
    }

    @Override
    public List<WriteOffActDto> loadLocalWriteOffActs() {
        Long currentLocationId = securityService.getCurrentLocationId();
        List<WriteOffActView> writeOffActViews = writeOffActRepo.findAllByLocationId(currentLocationId);
        return convertViewsToDtos(writeOffActViews);
    }

    private List<WriteOffActDto> convertViewsToDtos(List<WriteOffActView> writeOffActViews) {
        List<WriteOffActDto> writeOffActDtos = new ArrayList<>(writeOffActViews.size());

        for (WriteOffActView writeOffActView : writeOffActViews) {
            Set<WrittenOffItem> writtenOffItems = writeOffActView.getWrittenOffItems();
            Set<Long> itemIds = writtenOffItems.stream().map(writtenOffItem -> writtenOffItem.getItem().getId())
                    .collect(Collectors.toSet());

            long locationId = writeOffActView.getLocation().getId();
            long totalItemAmount = 0;
            float totalItemSum = 0;

            List<LocationItem> locationItems = locationItemRepo.findAllByLocationIdAndItemId(locationId, itemIds);

            for (WrittenOffItem writtenOffItem : writtenOffItems) {
                LocationItem locationItem = locationItems.stream()
                        .filter(li -> writtenOffItem.getItem().getId().equals(li.getItem().getId()))
                        .findAny()
                        .orElse(null);
// TODO:                       .orElseThrow(() -> new UndefinedItemException());

                int itemAmount = writtenOffItem.getAmount();
                totalItemAmount += itemAmount;
                totalItemSum += itemAmount * locationItem.getCost();
            }

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
