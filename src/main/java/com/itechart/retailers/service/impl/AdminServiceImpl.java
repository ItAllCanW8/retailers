package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.MailService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.exception.LocationIdentifierAlreadyExists;
import com.itechart.retailers.service.exception.LocationNotFoundException;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;
import com.itechart.retailers.service.exception.UserRoleNotApplicableToLocation;
import com.itechart.retailers.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.itechart.retailers.security.constant.Authority.DIRECTOR_ROLE;
import static com.itechart.retailers.service.constant.LogMessage.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	public static final String DISPATCHER_LABEL = "DISPATCHER";
	public static final String OFFLINE_SHOP_LABEL = "OFFLINE_SHOP";
	public static final String WAREHOUSE_MANAGER_LABEL = "WAREHOUSE_MANAGER";
	public static final String SHOP_MANAGER_LABEL = "SHOP_MANAGER";
	public static final String WAREHOUSE_LABEL = "WAREHOUSE";

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final SecurityContextService securityService;
	private final LocationRepository locationRepo;
	private final UserRepository userRepo;
	private final AddressRepository addressRepo;
	private final RoleService roleService;
	private final WarehouseRepository warehouseRepo;
	private final SupplierRepository supplierRepo;
	private final CustomerRepository customerRepo;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	@Override
	@Transactional
	public Location createLocation(Location location) throws LocationIdentifierAlreadyExists {
		addressRepo.save(location.getAddress());
		Customer customer = new Customer(securityService.getCurrentCustomerId());
		if (locationRepo.findLocationByIdentifierAndCustomerId(location.getIdentifier(), customer.getId()).isPresent()) {
			throw new LocationIdentifierAlreadyExists();
		}
		location.setCustomer(customer);
		LOGGER.warn(String.format(LOG_CREATED_MSG, "Location", location.getIdentifier()));
		return locationRepo.save(location);
	}

	@Override
	@Transactional
	public void deleteLocation(Long id) {
		locationRepo.deleteById(id);
		LOGGER.warn(String.format(LOG_DELETED_MSG, "Location", id));
	}

	@Override
	@Transactional
	public void deleteLocations(Set<Long> ids) {
		locationRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	@Transactional
	public User createUser(User user)
			throws LocationNotFoundException, MailIsAlreadyInUse, UserRoleNotApplicableToLocation, IOException {
		// TODO: Password generation
		Customer customer = new Customer(securityService.getCurrentCustomerId());
		user.setCustomer(customer);
		if (userRepo.findUserByEmailAndCustomerId(user.getEmail(), user.getCustomer().getId()).isPresent()) {
			throw new MailIsAlreadyInUse();
		}
		String roleStr = user.getRole().getRole();
		String generatedPassword = PasswordGenerator.generatePassword();
		mailService.sendPassword(user.getEmail(), generatedPassword);
		user.setPassword(passwordEncoder.encode(generatedPassword));
		user.setActive(true);
		user.setCustomer(new Customer(securityService.getCurrentCustomerId()));
		Long addressId = addressRepo.save(user.getAddress()).getId();
		user.setAddress(new Address(addressId));
		user.setRole(roleService.save(roleStr));

		if (DIRECTOR_ROLE.equals(roleStr)) {
			user.setLocation(null);
		} else {
			Location location = locationRepo.findLocationByIdentifier(user.getLocation().getIdentifier())
					.orElseThrow(LocationNotFoundException::new);
			user.setLocation(new Location(location.getId()));
			checkIfUserCanBeAppliedToLocation(roleStr, location.getType());
		}
		LOGGER.warn(String.format(LOG_CREATED_MSG, "User", user.getName()));
		return userRepo.save(user);
	}

	private void checkIfUserCanBeAppliedToLocation(String roleStr, String locationType)
			throws UserRoleNotApplicableToLocation {
		if (DISPATCHER_LABEL.equals(roleStr) && OFFLINE_SHOP_LABEL.equals(locationType) ||
				WAREHOUSE_MANAGER_LABEL.equals(roleStr) && OFFLINE_SHOP_LABEL.equals(locationType) ||
				SHOP_MANAGER_LABEL.equals(roleStr) && WAREHOUSE_LABEL.equals(locationType)
		) {
			throw new UserRoleNotApplicableToLocation();
		}
	}

	@Override
	@Transactional
	public void updateUserStatus(Long id, boolean isActive) {
		userRepo.changeUserStatus(id, isActive);
		LOGGER.warn(String.format(LOG_UPDATED_MSG, "User", id));
	}

	@Override
	@Transactional
	public Supplier createSupplier(Supplier supplier) {
		supplier = supplierRepo.save(supplier);
		Set<Warehouse> warehouses = supplier.getWarehouses();
		for (Warehouse wh : warehouses) {
			wh.setSupplier(supplier);
			addressRepo.save(wh.getAddress());
		}
		warehouseRepo.saveAll(warehouses);
		customerRepo.findById(securityService.getCurrentCustomerId()).get().getSuppliers().add(supplier);
		LOGGER.warn(String.format(LOG_CREATED_MSG, "Supplier", supplier.getIdentifier()));
		return supplier;
	}

	@Override
	public List<Supplier> findSuppliers() {
		return supplierRepo.findByCustomers_Id(securityService.getCurrentCustomerId());
	}

	@Override
	@Transactional
	public void updateSupplierStatus(Long id, boolean isActive) {
		supplierRepo.changeSupplierStatus(id, isActive);
		LOGGER.warn(String.format(LOG_UPDATED_MSG, "Supplier", id));
	}
}
