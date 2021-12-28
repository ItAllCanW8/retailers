package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.*;
import com.itechart.retailers.model.entity.projection.UserView;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SecurityContextService securityService;
    private final LocationRepository locationRepo;
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;
    private final RoleService roleService;
    private final WarehouseRepository warehouseRepo;
    private final SupplierRepository supplierRepo;
    private final CustomerRepository customerRepo;

    @Override
    public List<Location> findLocations() {
        return locationRepo.findLocationsByCustomerId(securityService.getCurrentCustomerId());
    }

    @Override
    @Transactional
    public boolean createLocation(Location location) {
        addressRepo.save(location.getAddress());
        location.setCustomer(new Customer(securityService.getCurrentCustomerId()));

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
    public List<UserView> findEmployees() {
        return userRepo.findUserViewsByCustomerId(securityService.getCurrentCustomerId());
    }

    @Override
    @Transactional
    public boolean createUser(User user) {
        user.setCustomer(new Customer(securityService.getCurrentCustomerId()));

        Long addressId = addressRepo.save(user.getAddress()).getId();
        user.setAddress(new Address(addressId));

        String roleStr = user.getRole().getRole();
        user.setRole(roleService.save(roleStr));

        if (roleStr.equals("DIRECTOR")) {
            user.setLocation(null);
        } else {
            String locationIdentifier = user.getLocation().getIdentifier();

            user.setLocation(new Location(locationRepo.findLocationByIdentifier(locationIdentifier).get().getId()));
        }

        return userRepo.save(user).getId() != null;
    }

    @Override
    @Transactional
    public void updateUserStatus(Long id, boolean isActive) {
        userRepo.changeUserStatus(id, isActive);
    }

    @Override
    @Transactional
    public boolean createSupplier(Supplier supplier) {
        supplierRepo.save(supplier);

        Set<Warehouse> warehouses = supplier.getWarehouses();

        for (Warehouse wh : warehouses) {
            wh.setSupplier(supplier);
            addressRepo.save(wh.getAddress());
        }

        warehouseRepo.saveAll(warehouses);

        customerRepo.findById(securityService.getCurrentCustomerId()).get().getSuppliers().add(supplier);

        return true;
    }

    @Override
    public List<Supplier> findSuppliers() {
        return supplierRepo.findByCustomers_Id(securityService.getCurrentCustomerId());
    }

    @Override
    @Transactional
    public void updateSupplierStatus(Long id, boolean isActive) {
        supplierRepo.changeSupplierStatus(id, isActive);
    }

    @Override
    public Optional<Long> findCustomerId(String adminEmail) {
        return Optional.of(userRepo.findByEmail(adminEmail).get().getCustomer().getId());
    }
}
