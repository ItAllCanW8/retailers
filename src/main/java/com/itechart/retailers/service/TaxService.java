package com.itechart.retailers.service;


import com.itechart.retailers.model.entity.StateCode;

import java.util.Optional;

public interface TaxService {

    Optional<Float> loadStateTax(StateCode stateCode);

    Optional<Float> loadRentalTax(Long locationId);

    Optional<Float> loadItemCategoryTax(Long customerId, Long categoryId);

    void updateRentalTax(Long locationId, Float newTax);

    void updateItemCategoryTax(Float newTax, Long customerId, Long categoryId);
}
