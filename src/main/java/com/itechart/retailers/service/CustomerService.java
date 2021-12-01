package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Customer;

import java.util.List;

public interface CustomerService {

	Customer save(Customer customer);

	List<Customer> findAll();

	Customer getById(Long id);

}
