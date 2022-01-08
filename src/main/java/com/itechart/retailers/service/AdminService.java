package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.service.exception.LocationIdentifierAlreadyExists;
import com.itechart.retailers.service.exception.LocationNotFoundException;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;

import java.util.List;
import java.util.Set;

public interface AdminService {

    boolean createLocation(Location location) throws LocationIdentifierAlreadyExists;

    void deleteLocation(Long id);

    void deleteLocations(Set<Long> ids);

    User createUser(User user) throws LocationNotFoundException, MailIsAlreadyInUse;

    void updateUserStatus(Long id, boolean isActive);

    Supplier createSupplier(Supplier supplier);

    List<Supplier> findSuppliers();

    void updateSupplierStatus(Long id, boolean isActive);
}
