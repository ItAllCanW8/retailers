package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    @Override
    public void changeUserStatus(Long customerId, boolean active) {
        Customer customer = customerRepository.getById(customerId);
        customer.setActive(active);
        customerRepository.save(customer);
        if (active) {
            User user = userRepository.getByRoleAndCustomerId(roleRepository.getByRole("ADMIN"), customerId);
            user.setActive(true);
            userRepository.save(user);
        } else {
            List<User> customerUsers = userRepository.findUsersByCustomerId(customerId);
            customerUsers.forEach(user -> user.setActive(false));
            userRepository.saveAll(customerUsers);
        }
    }
}
