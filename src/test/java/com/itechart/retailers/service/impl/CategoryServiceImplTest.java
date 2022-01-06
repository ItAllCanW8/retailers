package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.repository.CategoryRepository;
import com.itechart.retailers.repository.CustomerCategoryRepository;
import com.itechart.retailers.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock private CustomerCategoryRepository customerCategoryRepository;
    @Mock private CategoryRepository categoryRepository;
    private CategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryServiceImpl(categoryRepository, customerCategoryRepository);
    }

    @Test
    void shouldSaveCustomerCategory(){
        //given
        Category category = new Category("bread");
        category.setId(1L);
        Long customerId = 1L;
        given(categoryRepository.findByName(category.getName())).willReturn(Optional.of(category));
        given(customerCategoryRepository.findByCustomerIdAndCategoryId(customerId, category.getId()))
                .willReturn(Optional.empty());
        //when
        underTest.saveIfNotExists(category, customerId);
        //then
        ArgumentCaptor<Long> customerIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> categoryIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(customerCategoryRepository).findByCustomerIdAndCategoryId(customerIdArgumentCaptor.capture(),
                categoryIdArgumentCaptor.capture());
        assertThat(customerIdArgumentCaptor.getValue()).isEqualTo(customerId);
        assertThat(categoryIdArgumentCaptor.getValue()).isEqualTo(category.getId());

        Customer customer = new Customer();
        customer.setId(customerId);
        ArgumentCaptor<CustomerCategory> customerCategoryArgumentCaptor =
                ArgumentCaptor.forClass(CustomerCategory.class);
        verify(customerCategoryRepository).save(customerCategoryArgumentCaptor.capture());
    }

    @Test
    void shouldSaveCategoryAndCustomerCategory() {
        //given
        Category category = new Category("bread");
        Long customerId = 1L;

        given(categoryRepository.findByName(category.getName())).willReturn(Optional.empty());
        //when
        underTest.saveIfNotExists(category, customerId);
        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        assertThat(categoryArgumentCaptor.getValue()).isEqualTo(category);

        ArgumentCaptor<CustomerCategory> customerCategoryArgumentCaptor =
                ArgumentCaptor.forClass(CustomerCategory.class);
        verify(customerCategoryRepository).save(customerCategoryArgumentCaptor.capture());
    }

    @Test
    void shouldLoadCustomerCategories() {
        //given
        long customerId = 1L;
        //when
        underTest.loadCustomerCategories(customerId);
        //then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(customerCategoryRepository).findAllByCustomerId(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(customerId);
    }
}