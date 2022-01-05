package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.repository.CategoryRepository;
import com.itechart.retailers.repository.CustomerCategoryRepository;
import com.itechart.retailers.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CustomerCategoryRepository customerCategoryRepository;

    @Override
    public Category saveIfNotExists(Category category, Long customerId) {
        Optional<Category> categoryDB = categoryRepository.findByName(category.getName());
        if (categoryDB.isPresent()) {
            category = categoryDB.get();
            Optional<CustomerCategory> customerCategory =
                    customerCategoryRepository.findByCustomerIdAndCategoryId(customerId, category.getId());
            if (customerCategory.isEmpty()) {
                Customer customer = new Customer();
                customer.setId(customerId);
                customerCategoryRepository.save(CustomerCategory.builder()
                        .customer(customer)
                        .category(category)
                        .build());
            }
        } else {
            Customer customer = new Customer();
            customer.setId(customerId);
            category = categoryRepository.save(category);
            customerCategoryRepository.save(CustomerCategory.builder()
                    .category(category)
                    .customer(customer)
                    .build());
        }
        return category;
    }

    @Override
    public List<CustomerCategory> loadCustomerCategories(Long customerId) {
        return customerCategoryRepository.findAllByCustomerId(customerId);
    }
}
