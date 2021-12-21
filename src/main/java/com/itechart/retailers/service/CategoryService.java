package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Category;

public interface CategoryService {

    Category saveIfNotExists(Category category, Long customerId);

}
