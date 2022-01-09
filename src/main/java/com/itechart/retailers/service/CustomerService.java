package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.payload.response.CustomerPageResp;

public interface CustomerService {

    Customer save(Customer customer);

    CustomerPageResp findByParams(Boolean onlyActive, Integer page);

    Customer getById(Long id);

    void changeUserStatus(Long customerId, boolean active);

}
