package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final LocationRepository locationRepo;
	private final CustomerRepository customerRepo;
	private final UserRepository userRepo;
	private final AddressRepository addressRepo;

	@Override
	public List<Location> findLocations(Long customerId) {
		return locationRepo.findLocationsByCustomerId(customerId);
	}

	@Override
	@Transactional
	public boolean createLocation(Location location) {
		addressRepo.save(location.getAddress());

		return locationRepo.save(location).getId() != null;
	}

	@Override
	@Transactional
	public void deleteLocation(Long id) {
		locationRepo.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteLocations(Set<Long> ids) {
		locationRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public Optional<Long> findCustomerId(String adminEmail) {
		return Optional.of(userRepo.findByEmail(adminEmail).get().getCustomer().getId());
	}
}
