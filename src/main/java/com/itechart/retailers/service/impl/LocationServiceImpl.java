package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final SecurityContextService securityContextService;
	private final ApplicationService applicationService;
	private final ApplicationRepository applicationRepository;
	private final ApplicationItemRepository applicationItemRepository;
	private final LocationRepository locationRepository;
	private final LocationItemRepository locationItemRepository;
	private final StateTaxRepository stateTaxRepository;
	private final CustomerCategoryRepository customerCategoryRepository;

	private final SecurityContextService securityService;

	@Override
	public boolean canAcceptApplication(Long applicationId) {
		Integer applicationOccupiedCapacity = applicationService.getOccupiedCapacity(applicationId);
		Integer locationAvailableCapacity = getCurrentAvailableCapacity();
		return applicationOccupiedCapacity <= locationAvailableCapacity;
	}

	@Override
	public Integer getCurrentAvailableCapacity() {
		int occupiedCapacity = 0;
		Location location = securityContextService.getCurrentLocation();
		for (LocationItem locationItem : location.getItemAssoc()) {
			occupiedCapacity += locationItem.getItem().getUnits() * locationItem.getAmount();
		}
		return location.getTotalCapacity() - occupiedCapacity;
	}

	@Override
	public void acceptApplication(Long applicationId) {
		Set<ApplicationItem> applicationItems = applicationRepository.getById(applicationId).getItemAssoc();

		Set<LocationItem> locationItems = applicationItems.stream()
				.map(this::createLocationItem)
				.collect(Collectors.toSet());
		locationItemRepository.saveAll(locationItems);

		Application application = applicationService.getById(applicationId);
		application.setLastUpdDateTime(LocalDateTime.now());
		application.setLastUpdBy(securityContextService.getCurrentUser());
		application.setStatus("FINISHED_PROCESSING");
		applicationRepository.save(application);
		applicationItemRepository.deleteAll(application.getItemAssoc());
	}

	private LocationItem createLocationItem(ApplicationItem ai) {
		if (ai.getApplication().getDestLocation().getType().equals("OFFLINE_SHOP")) {
			Long currentCustomerId = securityService.getCurrentCustomer().getId();

			Float rentalTaxRate = ai.getApplication().getDestLocation().getRentalTaxRate();
			StateTax stateTax = stateTaxRepository.getByStateCode(StateCode.valueOf(ai.getApplication().getDestLocation().getAddress().getStateCode()));
			CustomerCategory customerCategory = customerCategoryRepository.findByCustomerIdAndCategoryId(currentCustomerId, ai.getItem().getCategory().getId()).get();
			Float categoryTaxRate = customerCategory.getCategoryTax();

			Float itemPrice = ai.getCost() * (1 + rentalTaxRate / 100 + stateTax.getTax() / 100 + categoryTaxRate / 100);

			return new LocationItem(ai.getAmount(), ai.getCost(), securityContextService.getCurrentLocation(), ai.getItem(), itemPrice);
		} else {
			return new LocationItem(ai.getAmount(), ai.getCost(), securityContextService.getCurrentLocation(), ai.getItem());
		}
	}

}
