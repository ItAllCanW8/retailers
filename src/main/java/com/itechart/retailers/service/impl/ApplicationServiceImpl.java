package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.CategoryService;
import com.itechart.retailers.service.LocationItemService;
import com.itechart.retailers.service.TaxService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.exception.UndefinedLocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private final LocationItemService locationItemService;
	private final LocationItemRepository locationItemRepository;
	private final StateTaxRepository stateTaxRepository;
	private final CustomerCategoryRepository customerCategoryRepository;

	@Override
	public List<Application> getCurrentApplications(Long customerId) {
		return applicationRepository.findApplicationsByCustomerId(customerId);
	}

	@Override
	@Transactional
	public void save(ApplicationReq applicationReq, Long customerId) throws UndefinedItemException {
		Set<Optional<Item>> optionalItems = applicationReq.getItems().stream()
				.map(ai -> itemRepository.findItemByUpc(ai.getUpc())).collect(Collectors.toSet());

		for (Optional<Item> item : optionalItems) {
			if (item.isEmpty()) {
				throw new UndefinedItemException();
			}
		}

		Application application = createApplication(applicationReq.getApplicationNumber(), securityService.getCurrentLocation());

		Set<ApplicationItem> itemsAssoc = applicationReq.getItems().stream()
				.map(ai -> ApplicationItem.builder()
						.item(itemRepository.findItemByUpc(ai.getUpc()).get())
						.application(application)
						.amount(ai.getAmount())
						.cost(ai.getCost())
						.build())
				.collect(Collectors.toSet());
		Customer customer = new Customer();
		customer.setId(customerId);
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

	@Override
	@Transactional
	public void dispatchItems(DispatchItemReq dispatchItemReq, Long customerId) throws ItemAmountException {
		Location location = locationRepository.findLocationByIdentifier(dispatchItemReq.getDestLocation()).get();

		Application application = createApplication(dispatchItemReq.getApplicationNumber(), location);

		Customer customer = new Customer();
		customer.setId(customerId);

		for (LocationItemResp enteredLocationItem : dispatchItemReq.getItemsToDispatch()) {
			if (enteredLocationItem.getAmount() == null) {
				enteredLocationItem.setAmount(0);
			}
			if (enteredLocationItem.getAmount() < 0 || enteredLocationItem.getAmount() > getActualLocationItem(enteredLocationItem.getUpc()).getAmount()) {
				throw new ItemAmountException();
			}
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

	private LocationItem getActualLocationItem(String upc) {
		return locationItemRepository.findByLocation(securityService.getCurrentLocation())
				.stream()
				.filter(li -> li.getItem().getUpc().equals(upc))
				.findFirst().get();
	}

	private Application createApplication(String applicationNumber, Location destLocation) {
		User currentUser = securityService.getCurrentUser();
		return Application.builder()
				.applicationNumber(applicationNumber)
				.destLocation(destLocation)
				.status("STARTED_PROCESSING")
				.createdBy(currentUser)
				.lastUpdBy(currentUser)
				.regDateTime(LocalDateTime.now())
				.lastUpdDateTime(LocalDateTime.now())
				.build();
	}


}
