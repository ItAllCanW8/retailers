package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findByParams(Boolean isOnlyActive) {
        if (isOnlyActive == null) {
            return customerRepository.findByOrderByIdDesc();
        } else {
            return customerRepository.findByIsActiveOrderByIdDesc(isOnlyActive);
        }
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }
}
