package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.CustomerCategory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerCategoryRepositoryTest {

    @Autowired
    private CustomerCategoryRepository underTest;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void shouldUpdateItemCategoryTax() {
        //given
        float newTax = 0.05f;

        Customer customer = Customer.builder()
                .name("Customer")
                .regDate(LocalDate.now())
                .isActive(true)
                .build();
        customer = customerRepository.save(customer);

        Category category = Category.builder()
                .name("Bread")
                .build();
        category = categoryRepository.save(category);

        CustomerCategory customerCategory = CustomerCategory.builder()
                .category(category)
                .customer(customer)
                .categoryTax(0.0f)
                .build();
        customerCategory = underTest.save(customerCategory);
        //when
        int result = underTest.updateItemCategoryTax(customerCategory.getId(), newTax);
        //then
        assertThat(result).isEqualTo(1);
    }
}