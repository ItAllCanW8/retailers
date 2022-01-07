package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.entity.projection.UserView;
import com.itechart.retailers.service.exception.UndefinedLocationException;

import java.util.List;
import java.util.Set;

public interface AdminService {

    Location createLocation(Location location);

    void deleteLocation(Long id);

    void deleteLocations(Set<Long> ids);

    List<UserView> getUsers();

    User createUser(User user) throws UndefinedLocationException;

    void updateUserStatus(Long id, boolean isActive);

    Supplier createSupplier(Supplier supplier);

    List<Supplier> findSuppliers();

    void updateSupplierStatus(Long id, boolean isActive);
}
