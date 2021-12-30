package com.itechart.retailers.service;


import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.StateCode;
import com.itechart.retailers.service.exception.IncorrectTaxException;

import java.util.List;
import java.util.Optional;

public interface TaxService {

    Optional<Float> loadStateTax(StateCode stateCode);

    Optional<Float> loadRentalTax(Long locationId);

    Optional<Float> loadItemCategoryTax(Long customerId, Long categoryId);

    void updateRentalTax(List<Location> rentalTaxes) throws IncorrectTaxException;

    void updateItemCategoryTaxes(List<CustomerCategory> categoryTaxes) throws IncorrectTaxException;
}
