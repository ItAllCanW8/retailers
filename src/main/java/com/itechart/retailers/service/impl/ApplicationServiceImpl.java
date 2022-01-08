package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itechart.retailers.controller.constant.Message.*;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final LocationRepository locationRepository;
	private final SecurityContextService securityService;
	private final ItemRepository itemRepository;
	private final ApplicationItemRepository applicationItemRepository;
	private final LocationItemRepository locationItemRepository;

	@Override
	public List<Application> getCurrentApplications() {
		return applicationRepository.findApplicationsByCustomerId(securityService.getCurrentCustomerId());
	}

	@Override
	@Transactional
	public void save(ApplicationReq applicationReq) throws ItemNotFoundException {
		Set<Optional<Item>> optionalItems = applicationReq.getItems().stream()
				.map(ai -> itemRepository.findItemByUpc(ai.getUpc())).collect(Collectors.toSet());
		for (Optional<Item> item : optionalItems) {
			if (item.isEmpty()) {
				throw new ItemNotFoundException(ITEM_NOT_FOUND_MSG);
			}
		}
		Application application = createApplication(applicationReq.getApplicationNumber(), securityService.getCurrentLocation());
		if (applicationReq.getItems().stream().anyMatch(item -> itemRepository.findItemByUpc(item.getUpc()).isEmpty())) {
			throw new ItemNotFoundException();
		}
		Set<ApplicationItem> itemsAssoc = applicationReq.getItems().stream()
				.map(ai -> ApplicationItem.builder()
						.item(itemRepository.findItemByUpc(ai.getUpc()).get())
						.application(application)
						.amount(ai.getAmount())
						.cost(ai.getCost())
						.build())
				.collect(Collectors.toSet());
		Customer customer = new Customer();
		customer.setId(securityService.getCurrentCustomerId());
		application.setCustomer(customer);
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
	public void forwardApplication(Long id, String locationIdentifier) throws LocationNotFoundException {
		Location location = locationRepository.findLocationByIdentifier(locationIdentifier)
				.orElseThrow(LocationNotFoundException::new);
		Application application = applicationRepository.getById(id);
		application.setLastUpdBy(securityService.getCurrentUser());
		application.setLastUpdDateTime(LocalDateTime.now());
		application.setSrcLocation(securityService.getCurrentLocation());
		application.setDestLocation(location);
		applicationRepository.save(application);
	}

	@Override
	@Transactional
	public void dispatchItems(DispatchItemReq dispatchItemReq) throws ItemAmountException, DispatchItemException, ItemNotFoundException, LocationNotFoundException {
		Optional<Location> location = locationRepository.findLocationByIdentifier(dispatchItemReq.getDestLocation());
		if (location.isEmpty()) {
			throw new LocationNotFoundException();
		}
		Application application = createApplication(dispatchItemReq.getApplicationNumber(), location.get());
		Customer customer = new Customer();
		customer.setId(securityService.getCurrentCustomerId());
		for (LocationItemResp enteredLocationItem : dispatchItemReq.getItemsToDispatch()) {
			if (enteredLocationItem.getAmount() == null) {
				enteredLocationItem.setAmount(0);
			}
			if (enteredLocationItem.getAmount() < 0 || enteredLocationItem.getAmount() > getActualLocationItem(enteredLocationItem.getUpc()).getAmount()) {
				throw new ItemAmountException(INCORRECT_ITEM_AMOUNT_INPUT_MSG);
			}
		}

		if (dispatchItemReq.getItemsToDispatch().stream().allMatch(item -> item.getAmount() == 0)) {
			throw new DispatchItemException(DISPATCH_ITEM_EXCEPTION_MSG);
		}

		for (LocationItemResp locationItemResp : dispatchItemReq.getItemsToDispatch()) {
			LocationItem locationItem = locationItemRepository.getByItemUpcAndLocation(locationItemResp.getUpc(), securityService.getCurrentLocation());
			locationItem.setAmount(locationItem.getAmount() - locationItemResp.getAmount());
			locationItemRepository.save(locationItem);
		}

		Set<ApplicationItem> itemsAssoc = dispatchItemReq.getItemsToDispatch().stream()
				.map(ai -> ApplicationItem.builder()
						.item(itemRepository.findItemByUpc(ai.getUpc()).get())
						.application(application)
						.amount(ai.getAmount())
						.cost(ai.getCost())
						.build())
				.collect(Collectors.toSet());
		application.setCustomer(customer);
		applicationRepository.save(application);
		applicationItemRepository.saveAll(itemsAssoc);
	}

	private LocationItem getActualLocationItem(String upc) throws ItemNotFoundException {
		Location location = securityService.getCurrentLocation();
		Optional<LocationItem> locationItem = locationItemRepository.findByLocationAndItemUpc(location, upc);
		if (locationItem.isPresent()) {
			return locationItem.get();
		} else {
			throw new ItemNotFoundException();
		}
	}

	private Application createApplication(String applicationNumber, Location destLocation) {
		User currentUser = securityService.getCurrentUser();
		return Application.builder()
				.applicationNumber(applicationNumber)
				.srcLocation(securityService.getCurrentLocation())
				.destLocation(destLocation)
				.status("STARTED_PROCESSING")
				.createdBy(currentUser)
				.lastUpdBy(currentUser)
				.regDateTime(LocalDateTime.now())
				.lastUpdDateTime(LocalDateTime.now())
				.build();
	}
}
