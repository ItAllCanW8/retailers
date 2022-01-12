package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.service.exception.LocationIdentifierAlreadyExists;
import com.itechart.retailers.service.exception.LocationNotFoundException;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;
import com.itechart.retailers.service.exception.UserRoleNotApplicableToLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * The interface Admin service.
 */
public interface AdminService {

    /**
     * Create location location.
     *
     * @param location the location
     * @return the location
     * @throws LocationIdentifierAlreadyExists the location identifier already exists
     */
    Location createLocation(Location location) throws LocationIdentifierAlreadyExists;

    /**
     * Delete location.
     *
     * @param id the id
     */
    void deleteLocation(Long id);

    /**
     * Delete locations.
     *
     * @param ids the ids
     */
    void deleteLocations(Set<Long> ids);

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     * @throws LocationNotFoundException       the location not found exception
     * @throws MailIsAlreadyInUse              the mail is already in use
     * @throws UserRoleNotApplicableToLocation the user role not applicable to location
     * @throws IOException                     the io exception
     */
    User createUser(User user) throws LocationNotFoundException, MailIsAlreadyInUse, UserRoleNotApplicableToLocation, IOException;

    /**
     * Update user status.
     *
     * @param id       the id
     * @param isActive the is active
     */
    void updateUserStatus(Long id, boolean isActive);

    /**
     * Create supplier supplier.
     *
     * @param supplier the supplier
     * @return the supplier
     */
    Supplier createSupplier(Supplier supplier);

    /**
     * Find suppliers list.
     *
     * @return the list
     */
    List<Supplier> findSuppliers();

    /**
     * Update supplier status.
     *
     * @param id       the id
     * @param isActive the is active
     */
    void updateSupplierStatus(Long id, boolean isActive);
}
