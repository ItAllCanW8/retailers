package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.CustomerCategory;

import java.util.List;

public interface CategoryService {

    Category saveIfNotExists(Category category, Long customerId);

    List<CustomerCategory> loadCustomerCategories(Long customerId);
}
