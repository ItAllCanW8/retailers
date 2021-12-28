package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.StateCode;
import com.itechart.retailers.repository.CustomerCategoryRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.repository.StateTaxRepository;
import com.itechart.retailers.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {
    private final StateTaxRepository stateTaxRepo;
    private final LocationRepository locationRepo;
    private final CustomerCategoryRepository customerCategoryRepo;

    @Override
    public Optional<Float> loadStateTax(StateCode stateCode) {
        return Optional.of(stateTaxRepo.getByStateCode(stateCode).getTax());
    }

    @Override
    @Transactional
    public Optional<Float> loadRentalTax(Long locationId) {
        return Optional.of(locationRepo.getById(locationId).getRentalTaxRate());
    }

    @Override
    public Optional<Float> loadItemCategoryTax(Long customerId, Long categoryId) {
        return Optional.of(customerCategoryRepo.findByCustomerIdAndCategoryId(customerId, categoryId).get()
                .getCategoryTax());
    }

    @Override
    @Transactional
    public void updateRentalTax(List<Location> locations) {
        for (Location location : locations) {
            locationRepo.updateRentalTax(location.getId(), location.getRentalTaxRate());
        }
    }

    @Override
    @Transactional
    public void updateItemCategoryTaxes(List<CustomerCategory> customerCategories) {
        for (CustomerCategory customerCategory : customerCategories) {
            customerCategoryRepo.updateItemCategoryTax(customerCategory.getId(), customerCategory.getCategoryTax());
        }
    }
}
