package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.*;
import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.model.dto.ItemDto;
import com.itechart.retailers.model.dto.UserDto;
import com.itechart.retailers.repository.ApplicationRepository;
import com.itechart.retailers.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public void save(ApplicationDto applicationDto) {
        try {
            applicationRepository.save(convertToEntity(applicationDto));
        } catch (NumberFormatException e) {
            //log exception
        }
    }

    @Override
    public Application getById(Long id) {
        return applicationRepository.getById(id);
    }

    @Override
    public void delete(Application application) {
        applicationRepository.delete(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    private Application convertToEntity(ApplicationDto applicationDto) {
        UserDto createdBy = applicationDto.getCreatedBy();
        Set<ItemDto> itemDtos = applicationDto.getItems();

        Application application = Application.builder()
                .applicationNumber(applicationDto.getApplicationNumber())
                .itemsTotal(Long.valueOf(applicationDto.getItemsTotal()))
                .unitsTotal(Long.valueOf(applicationDto.getUnitsTotal()))
                .srcLocation(Location.builder()
                        .identifier(applicationDto.getSrcLocation()).build())
                .destLocation(Location.builder()
                        .identifier(applicationDto.getDestLocation()).build())
                .createdBy(User.builder()
                        .name(createdBy.getName())
                        .surname(createdBy.getSurname())
                        .email(createdBy.getEmail()).build())
                .build();

        Set<ApplicationItem> applicationItems = new HashSet<>();

        for (ItemDto itemDto : itemDtos) {
            applicationItems.add(ApplicationItem.builder()
                    .item(Item.builder()
                            .upc(itemDto.getUpc()).build())
                    .amount(itemDto.getAmount())
                    .cost(itemDto.getCost())
                    .application(application)
                    .build());
        }

        application.setItemAssoc(applicationItems);

        return application;
    }
}
