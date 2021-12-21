package com.itechart.retailers.service;


import com.itechart.retailers.model.entity.StateCode;

public interface TaxService {

    Float loadStateTax(StateCode stateCode);

    Float loadRentalTax(Long locationId);

    Float loadItemCategoryTax(Long customerId, Long categoryId);

    void updateRentalTax(Long locationId, Float newTax);

    void updateItemCategoryTax(Float newTax, Long customerId, Long categoryId);
}
