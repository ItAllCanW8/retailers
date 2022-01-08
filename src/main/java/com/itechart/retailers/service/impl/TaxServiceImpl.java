package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.enumeration.StateCode;
import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.repository.CustomerCategoryRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.repository.StateTaxRepository;
import com.itechart.retailers.service.TaxService;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.IncorrectTaxException;
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
    public Optional<Float> loadItemCategoryTax(Long customerId, Long categoryId) throws CustomerCategoryNotFoundException {
        Optional<CustomerCategory> optionalCustomerCategory = customerCategoryRepo.findByCustomerIdAndCategoryId(customerId, categoryId);
        if (optionalCustomerCategory.isPresent()) {
            return Optional.of(optionalCustomerCategory.get()
                    .getCategoryTax());
        } else {
            throw new CustomerCategoryNotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateRentalTax(List<Location> locations) throws IncorrectTaxException {
        for (Location location : locations) {
            Float rentalTaxRate = location.getRentalTaxRate();
            if (rentalTaxRate < 0) {
                throw new IncorrectTaxException();
            } else {
                locationRepo.updateRentalTax(location.getId(), rentalTaxRate);
            }
        }
    }

    @Override
    @Transactional
    public void updateItemCategoryTaxes(List<CustomerCategory> customerCategories) throws IncorrectTaxException {
        for (CustomerCategory customerCategory : customerCategories) {
            Float categoryTaxRate = customerCategory.getCategoryTax();
            if (categoryTaxRate < 0) {
                throw new IncorrectTaxException();
            } else {
                customerCategoryRepo.updateItemCategoryTax(customerCategory.getId(), categoryTaxRate);
            }
        }
    }
}
