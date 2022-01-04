package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.StateCode;
import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationItemRepository applicationItemRepository;
    private final LocationItemRepository locationItemRepository;
    private final LocationRepository locationRepository;
    private final StateTaxRepository stateTaxRepository;
    private final CustomerCategoryRepository customerCategoryRepository;

    private final SecurityContextService securityService;

    @Override
    public List<Location> getLocations(Boolean exceptCurrent) {
        if (exceptCurrent == null) {
            return locationRepository.findLocationsByCustomerId(securityService.getCurrentCustomerId());
        }
        Long currentCustomerId = securityService.getCurrentCustomer().getId();
        Long currentLocationId = securityService.getCurrentLocation().getId();
        return locationRepository.findLocationsByCustomerIdAndIdNot(currentCustomerId, currentLocationId);
    }

    @Override
    public Integer getCurrentAvailableCapacity() {
        int occupiedCapacity = 0;
        Location location = securityService.getCurrentLocation();
        for (LocationItem locationItem : location.getItemAssoc()) {
            occupiedCapacity += locationItem.getItem().getUnits() * locationItem.getAmount();
        }
        return location.getTotalCapacity() - occupiedCapacity;
    }

    @Override
    public void acceptApplication(Long applicationId) throws TaxesNotDefinedException, ItemAmountException {
        canAcceptItems(applicationId);

        Set<ApplicationItem> applicationItems = applicationRepository.getById(applicationId).getItemAssoc();

        Set<LocationItem> locationItems = new HashSet<>();
        for (ApplicationItem applicationItem : applicationItems) {
            locationItems.add(createLocationItem(applicationItem));
        }

        for (LocationItem locationItem : locationItems) {
            Optional<LocationItem> locationItemDB =
                    locationItemRepository.findLocationItemByItemAndLocation(locationItem.getItem(),
                            locationItem.getLocation());
            if (locationItemDB.isPresent()) {
                if (locationItemDB.get().getCost() > locationItem.getCost()) {
                    locationItem.setCost(locationItemDB.get().getCost());
                    locationItem.setPrice(locationItemDB.get().getPrice());
                    locationItem.setId(locationItemDB.get().getId());
                }
                locationItem.setAmount(locationItemDB.get().getAmount() + locationItem.getAmount());
                locationItemRepository.deleteById(locationItemDB.get().getId());
            }
            locationItemRepository.save(locationItem);
        }

        Application application = applicationService.getById(applicationId);
        application.setLastUpdDateTime(LocalDateTime.now());
        application.setLastUpdBy(securityService.getCurrentUser());
        application.setStatus("FINISHED_PROCESSING");
        applicationRepository.save(application);
        applicationItemRepository.deleteAll(application.getItemAssoc());
    }

    private void canAcceptItems(Long applicationId) throws ItemAmountException {
        Integer applicationOccupiedCapacity = applicationService.getOccupiedCapacity(applicationId);
        Integer locationAvailableCapacity = getCurrentAvailableCapacity();
        if (applicationOccupiedCapacity >= locationAvailableCapacity) {
            throw new ItemAmountException();
        }
    }

    private LocationItem createLocationItem(ApplicationItem applicationItem) throws TaxesNotDefinedException {
        if ("OFFLINE_SHOP".equals(applicationItem.getApplication().getDestLocation().getType())) {
            Long currentCustomerId = securityService.getCurrentCustomer().getId();

            Float rentalTaxRate = applicationItem.getApplication().getDestLocation().getRentalTaxRate();
            StateTax stateTax = stateTaxRepository.getByStateCode
                    (StateCode.valueOf(applicationItem.getApplication().getDestLocation().getAddress().getStateCode()));
            CustomerCategory customerCategory = customerCategoryRepository.
                    findByCustomerIdAndCategoryId(currentCustomerId, applicationItem.getItem().getCategory().getId()).get();
            Float categoryTaxRate = customerCategory.getCategoryTax();

            if (rentalTaxRate == null || stateTax.getTax() == null || categoryTaxRate == null) {
                throw new TaxesNotDefinedException();
            }

            Float itemPrice = applicationItem.getCost() * (1f + rentalTaxRate * 0.01f + stateTax.getTax() * 0.01f + categoryTaxRate * 0.01f);

            return new LocationItem(applicationItem.getAmount(), applicationItem.getCost(),
                    securityService.getCurrentLocation(), applicationItem.getItem(),
                    itemPrice);
        } else {
            return new LocationItem(applicationItem.getAmount(), applicationItem.getCost(),
                    securityService.getCurrentLocation(), applicationItem.getItem());
        }
    }

}
