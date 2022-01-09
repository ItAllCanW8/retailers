package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.ApplicationPageResp;
import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Value("${pagination.pageSize}")
	private Integer pageSize;

	@Override
	public ApplicationPageResp getCurrentApplications(Integer page) {
		Page<Application> applications = applicationRepository.findApplicationsByCustomerIdOrderByIdDesc(
				securityService.getCurrentCustomerId(), PageRequest.of(page, pageSize)
		);
		return new ApplicationPageResp(applications.getContent(), applications.getTotalPages());
	}

	@Override
	@Transactional
	public void save(ApplicationReq applicationReq) throws ItemNotFoundException {
		checkIfItemsExist(applicationReq);

		Application application = createApplication(applicationReq.getApplicationNumber(),
				securityService.getCurrentLocation());
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

	private void checkIfItemsExist(ApplicationReq applicationReq) throws ItemNotFoundException {
		Set<Optional<Item>> optionalItems = applicationReq.getItems().stream()
				.map(applicationItem -> itemRepository.findItemByUpc(applicationItem.getUpc()))
				.collect(Collectors.toSet());
		for (Optional<Item> item : optionalItems) {
			if (item.isEmpty()) {
				throw new ItemNotFoundException(ITEM_NOT_FOUND_MSG);
			}
		}
		if (applicationReq.getItems().stream().anyMatch(item -> itemRepository.findItemByUpc(item.getUpc()).isEmpty())) {
			throw new ItemNotFoundException(ITEM_NOT_FOUND_MSG);
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

	@Override
	public List<Application> findApplicationsByDestLocation(Location destLocation) {
		return applicationRepository.findApplicationsByDestLocation(destLocation);
	}

	@Override
	public Integer getOccupiedCapacity(Long id) throws ApplicationNotFoundException {
		int occupiedCapacity = 0;
		Set<ApplicationItem> applicationItems = applicationRepository
				.findById(id)
				.orElseThrow(ApplicationNotFoundException::new)
				.getItemAssoc();
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
	public void dispatchItems(DispatchItemReq dispatchItemReq)
			throws ItemAmountException, DispatchItemException, ItemNotFoundException, LocationNotFoundException {

		validateDispatchRequest(dispatchItemReq);
		removeItemsFromLocation(dispatchItemReq);
		createApplication(dispatchItemReq);
	}

	private void createApplication(DispatchItemReq dispatchItemReq) throws LocationNotFoundException {
		Location location = locationRepository.findLocationByIdentifier(dispatchItemReq.getDestLocation())
				.orElseThrow(LocationNotFoundException::new);
		Application application = createApplication(dispatchItemReq.getApplicationNumber(), location);
		Customer customer = new Customer();
		customer.setId(securityService.getCurrentCustomerId());

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

	private void removeItemsFromLocation(DispatchItemReq dispatchItemReq) {
		for (LocationItemResp locationItemResp : dispatchItemReq.getItemsToDispatch()) {
			LocationItem locationItem = locationItemRepository.getByItemUpcAndLocation(
					locationItemResp.getUpc(), securityService.getCurrentLocation()
			);
			locationItem.setAmount(locationItem.getAmount() - locationItemResp.getAmount());
			locationItemRepository.save(locationItem);
		}
	}

	private void validateDispatchRequest(DispatchItemReq dispatchItemReq)
			throws ItemNotFoundException, ItemAmountException, DispatchItemException {
		if (dispatchItemReq.getItemsToDispatch().stream().allMatch(item -> item.getAmount() == 0)) {
			throw new DispatchItemException(DISPATCH_ITEM_EXCEPTION_MSG);
		}
		for (LocationItemResp enteredLocationItem : dispatchItemReq.getItemsToDispatch()) {
			if (enteredLocationItem.getAmount() == null) {
				enteredLocationItem.setAmount(0);
			}
			if (enteredLocationItem.getAmount() < 0 ||
					enteredLocationItem.getAmount() >
							getActualLocationItem(enteredLocationItem.getUpc()).getAmount()) {
				throw new ItemAmountException(INCORRECT_ITEM_AMOUNT_INPUT_MSG);
			}
		}
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
		Location currentLocation = securityService.getCurrentLocation();
		if (currentLocation.equals(destLocation)) {
			currentLocation = null;
		}
		return Application.builder()
				.applicationNumber(applicationNumber)
				.srcLocation(currentLocation)
				.destLocation(destLocation)
				.status("STARTED_PROCESSING")
				.createdBy(currentUser)
				.lastUpdBy(currentUser)
				.regDateTime(LocalDateTime.now())
				.lastUpdDateTime(LocalDateTime.now())
				.build();
	}
}
