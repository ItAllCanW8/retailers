package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.repository.ApplicationItemRepository;
import com.itechart.retailers.repository.ApplicationRepository;
import com.itechart.retailers.repository.ItemRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.exception.UndefinedLocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final LocationRepository locationRepository;
    private final SecurityContextService securityService;
    private final ItemRepository itemRepository;
    private final ApplicationItemRepository applicationItemRepository;

    @Override
    public List<Application> getCurrentApplications() {
        return applicationRepository.findAll();
        //TODO: return applicationRepository.findByCustomer(securityService.getCurrentCustomer());
    }

    @Override
    @Transactional
    public void save(ApplicationReq applicationReq) throws UndefinedItemException {
        User currentUser = securityService.getCurrentUser();

        Set<Optional<Item>> optionalItems = applicationReq.getItems().stream()
                .map(ai -> itemRepository.findItemByUpc(ai.getUpc())).collect(Collectors.toSet());

        for (Optional<Item> item : optionalItems) {
            if (item.isEmpty()) {
                throw new UndefinedItemException();
            }
        }

        Application application = Application.builder()
                .applicationNumber(applicationReq.getApplicationNumber())
                .destLocation(currentUser.getLocation())
                .status("STARTED_PROCESSING")
                .createdBy(currentUser)
                .lastUpdBy(currentUser)
                .regDateTime(LocalDateTime.now())
                .lastUpdDateTime(LocalDateTime.now())
                .build();

        Set<ApplicationItem> itemsAssoc = applicationReq.getItems().stream()
                .map(ai -> ApplicationItem.builder()
                        .item(itemRepository.findItemByUpc(ai.getUpc()).get())
                        .application(application)
                        .amount(ai.getAmount())
                        .cost(ai.getCost())
                        .build())
                .collect(Collectors.toSet());

        applicationRepository.save(application);
        applicationItemRepository.saveAll(itemsAssoc);
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

    @Override
    public List<Application> findApplicationsByDestLocation(Location destLocation) {
        return applicationRepository.findApplicationsByDestLocation(destLocation);
    }

    @Override
    public Integer getOccupiedCapacity(Long id) {
        int occupiedCapacity = 0;
        Set<ApplicationItem> applicationItems = applicationRepository.getById(id).getItemAssoc();
        for (ApplicationItem applicationItem : applicationItems) {
            occupiedCapacity += applicationItem.getItem().getUnits() * applicationItem.getAmount();
        }
        return occupiedCapacity;
    }

    @Override
    public void forwardApplication(Long id, String locationIdentifier) throws UndefinedLocationException {
        Location location = locationRepository.findLocationByIdentifier(locationIdentifier)
                .orElseThrow(UndefinedLocationException::new);
        Application application = applicationRepository.getById(id);
        application.setLastUpdBy(securityService.getCurrentUser());
        application.setLastUpdDateTime(LocalDateTime.now());
        application.setSrcLocation(securityService.getCurrentLocation());
        application.setDestLocation(location);
        applicationRepository.save(application);
    }


}
