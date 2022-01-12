package com.itechart.retailers.service;


import com.itechart.retailers.model.enumeration.StateCode;
import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.IncorrectTaxException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tax service.
 */
public interface TaxService {

    /**
     * Load state tax optional.
     *
     * @param stateCode the state code
     * @return the optional
     */
    Optional<Float> loadStateTax(StateCode stateCode);

    /**
     * Load rental tax optional.
     *
     * @param locationId the location id
     * @return the optional
     */
    Optional<Float> loadRentalTax(Long locationId);

    /**
     * Load item category tax optional.
     *
     * @param customerId the customer id
     * @param categoryId the category id
     * @return the optional
     * @throws CustomerCategoryNotFoundException the customer category not found exception
     */
    Optional<Float> loadItemCategoryTax(Long customerId, Long categoryId) throws CustomerCategoryNotFoundException;

    /**
     * Update rental tax.
     *
     * @param rentalTaxes the rental taxes
     * @throws IncorrectTaxException the incorrect tax exception
     */
    void updateRentalTax(List<Location> rentalTaxes) throws IncorrectTaxException;

    /**
     * Update item category taxes.
     *
     * @param categoryTaxes the category taxes
     * @throws IncorrectTaxException the incorrect tax exception
     */
    void updateItemCategoryTaxes(List<CustomerCategory> categoryTaxes) throws IncorrectTaxException;
}
