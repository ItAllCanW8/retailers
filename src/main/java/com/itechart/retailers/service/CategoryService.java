package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.CustomerCategory;

import java.util.List;

/**
 * The interface Category service.
 */
public interface CategoryService {

    /**
     * Save if not exists category.
     *
     * @param category   the category
     * @param customerId the customer id
     * @return the category
     */
    Category saveIfNotExists(Category category, Long customerId);

    /**
     * Load customer categories list.
     *
     * @param customerId the customer id
     * @return the list
     */
    List<CustomerCategory> loadCustomerCategories(Long customerId);
}
