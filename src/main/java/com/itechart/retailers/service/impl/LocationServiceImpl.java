package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.enumeration.StateCode;
import com.itechart.retailers.model.payload.response.LocationPageResp;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.ApplicationNotFoundException;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.itechart.retailers.controller.constant.Message.NO_SPACE_IN_LOCATION_MSG;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    public static final String FINISHED_PROCESSING_STATUS = "FINISHED_PROCESSING";
    public static final String OFFLINE_SHOP_LABEL = "OFFLINE_SHOP";
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationItemRepository applicationItemRepository;
    private final LocationItemRepository locationItemRepository;
    private final LocationRepository locationRepository;
    private final StateTaxRepository stateTaxRepository;
    private final CustomerCategoryRepository customerCategoryRepository;

    private final SecurityContextService securityService;

    @Value("${pagination.pageSize}")
    private Integer pageSize;

    @Override
    public LocationPageResp getLocations(Boolean exceptCurrent, Integer page) {
        Customer currentCustomer = securityService.getCurrentCustomer();
        if (exceptCurrent != null && exceptCurrent) {
            return getAllLocationsExceptCurrent(currentCustomer);
        }
        if (page == null) {
            return getAllLocations(currentCustomer);
        }
        return getLocationsPage(page, currentCustomer);
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
    public void acceptApplication(Long applicationId)
            throws TaxesNotDefinedException, ItemAmountException, ApplicationNotFoundException,
            CustomerCategoryNotFoundException {

        if (applicationRepository.findById(applicationId).isEmpty()) {
            throw new ApplicationNotFoundException();
        }
        canAcceptItems(applicationId);

        Set<ApplicationItem> applicationItems = applicationRepository.getById(applicationId).getItemAssoc();
        Set<LocationItem> locationItems = new HashSet<>();
        for (ApplicationItem applicationItem : applicationItems) {
            locationItems.add(createLocationItem(applicationItem));
        }

        saveLocationItems(locationItems);

        Application application = applicationService.getById(applicationId);
        application.setLastUpdDateTime(LocalDateTime.now());
        application.setLastUpdBy(securityService.getCurrentUser());
        application.setStatus(FINISHED_PROCESSING_STATUS);
        applicationRepository.save(application);
        applicationItemRepository.deleteAll(application.getItemAssoc());
    }

    private void saveLocationItems(Set<LocationItem> locationItems) {
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
    }

    private void canAcceptItems(Long applicationId) throws ItemAmountException, ApplicationNotFoundException {
        Integer applicationOccupiedCapacity = applicationService.getOccupiedCapacity(applicationId);
        Integer locationAvailableCapacity = getCurrentAvailableCapacity();
        if (applicationOccupiedCapacity >= locationAvailableCapacity) {
            throw new ItemAmountException(NO_SPACE_IN_LOCATION_MSG);
        }
    }

    private LocationItem createLocationItem(ApplicationItem applicationItem)
            throws TaxesNotDefinedException, CustomerCategoryNotFoundException {

        if (applicationItem.getApplication() != null &&
                applicationItem.getApplication().getDestLocation() != null &&
                applicationItem.getApplication().getDestLocation().getType() != null &&
                OFFLINE_SHOP_LABEL.equals(applicationItem.getApplication().getDestLocation().getType())) {

            Long currentCustomerId = securityService.getCurrentCustomer().getId();
            Float rentalTaxRate = applicationItem.getApplication().getDestLocation().getRentalTaxRate();
            StateTax stateTax = stateTaxRepository.getByStateCode
                    (StateCode.valueOf(applicationItem.getApplication().getDestLocation().getAddress().getStateCode()));
            Optional<CustomerCategory> optionalCustomerCategory = customerCategoryRepository.
                    findByCustomerIdAndCategoryId(currentCustomerId, applicationItem.getItem().getCategory().getId());
            if (optionalCustomerCategory.isEmpty()) {
                throw new CustomerCategoryNotFoundException();
            }
            CustomerCategory customerCategory = optionalCustomerCategory.get();
            Float categoryTaxRate = customerCategory.getCategoryTax();
            if (rentalTaxRate == null || stateTax.getTax() == null || categoryTaxRate == null) {
                throw new TaxesNotDefinedException();
            }

            Float percentageRate = 0.01F;
            Float itemPrice = applicationItem.getCost()
                    * (1f + rentalTaxRate * percentageRate
                    + stateTax.getTax() * percentageRate
                    + categoryTaxRate * percentageRate);
            return new LocationItem(applicationItem.getAmount(), applicationItem.getCost(),
                    securityService.getCurrentLocation(), applicationItem.getItem(),
                    itemPrice);
        } else {
            return new LocationItem(applicationItem.getAmount(), applicationItem.getCost(),
                    securityService.getCurrentLocation(), applicationItem.getItem());
        }
    }

    private LocationPageResp getAllLocationsExceptCurrent(Customer currentCustomer) {
        Long currentCustomerId = currentCustomer.getId();
        Long currentLocationId = securityService.getCurrentLocation().getId();
        List<Location> locations =
                locationRepository.findLocationsByCustomerIdAndIdNot(currentCustomerId, currentLocationId);
        return new LocationPageResp(locations, null);
    }

    private LocationPageResp getLocationsPage(Integer page, Customer currentCustomer) {
        Page<Location> locations =
                locationRepository.findLocationsByCustomer(currentCustomer, PageRequest.of(page, pageSize));
        return new LocationPageResp(locations.getContent(), locations.getTotalPages());
    }

    private LocationPageResp getAllLocations(Customer currentCustomer) {
        List<Location> locations = locationRepository.findLocationsByCustomer(currentCustomer);
        return new LocationPageResp(locations, null);
    }
}
