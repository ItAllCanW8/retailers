package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.model.dto.ItemDto;
import com.itechart.retailers.model.dto.UserDto;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.repository.ApplicationRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final LocationRepository locationRepository;
    private final SecurityContextService securityService;

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    @Transactional
    public void save(ApplicationReq applicationDto) {
        User currentUser = securityService.getCurrentUser();

        Set<ApplicationItem> itemsAssoc = applicationDto.getItems().stream().map(ai -> ApplicationItem.builder()
                        .item(Item.builder().upc(ai.getUpc()).build())
                        .amount(ai.getAmount())
                        .cost(ai.getCost())
                        .build())
                .collect(Collectors.toSet());

        Application application = Application.builder()
                .applicationNumber(applicationDto.getApplicationNumber())
                .destLocation(locationRepository.findLocationByIdentifier(applicationDto.getLocation().getIdentifier()).get())
                .status("Open")
                .itemAssoc(itemsAssoc)
                .createdBy(currentUser)
                .srcLocation(currentUser.getLocation())
                .regDateTime(LocalDateTime.now())
                .build();

        applicationRepository.save(application);
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
                    .item(Item.builder().upc(itemDto.getUpc()).build())
                    .amount(itemDto.getAmount())
                    .cost(itemDto.getCost())
                    .application(application)
                    .build());
        }

        application.setItemAssoc(applicationItems);

        return application;
    }
}
