package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.response.CustomerPageResp;
import com.itechart.retailers.model.payload.response.CustomerResp;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.CustomerService;
import com.itechart.retailers.service.MailService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;
import com.itechart.retailers.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.itechart.retailers.security.constant.Authority.ADMIN_ROLE;
import static com.itechart.retailers.service.constant.LogMessage.LOG_CREATED_MSG;
import static com.itechart.retailers.service.constant.LogMessage.LOG_UPDATED_MSG;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
    public static final String CUSTOMER_LABEL = "Customer";
    public static final String ADMIN_LABEL = "ADMIN";

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${pagination.pageSize}")
    private Integer pageSize;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerPageResp findByParams(Boolean active, Integer page) {
        Page<Customer> customers;
        if (active == null) {
            customers = customerRepository.findByOrderByIdDesc(PageRequest.of(page, pageSize));
        } else {
            customers = customerRepository.findByIsActiveOrderByIdDesc(active, PageRequest.of(page, pageSize));
        }
        return new CustomerPageResp(customers.getContent().stream()
                .map(customer -> new CustomerResp(customer,
                        userService.getByRoleAndCustomerId(roleService.getByRole(ADMIN_ROLE),
                                customer.getId()).getEmail()))
                .toList(), customers.getTotalPages());
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
            User user = userRepository.getByRoleAndCustomerId(roleRepository.getByRole(ADMIN_ROLE), customerId);
            user.setActive(true);
            userRepository.save(user);
        } else {
            List<User> customerUsers = userRepository.findUsersByCustomerId(customerId);
            customerUsers.forEach(user -> user.setActive(false));
            userRepository.saveAll(customerUsers);
        }
        LOGGER.warn(String.format(LOG_UPDATED_MSG, CUSTOMER_LABEL, customerId));
    }

    @Override
    public Customer registerCustomer(String name, String email) throws MailIsAlreadyInUse {
        if (userService.existsByEmail(email)) {
            throw new MailIsAlreadyInUse();
        }

        Customer customer = save(Customer.builder()
                .name(name)
                .regDate(LocalDate.now())
                .isActive(true)
                .build());

        String generatedPassword = PasswordGenerator.generatePassword();
        new PasswordEmailSender(email, generatedPassword, mailService).start();

        userService.save(User.builder()
                .name(name)
                .email(email)
                .role(roleService.save(ADMIN_LABEL))
                .password(passwordEncoder.encode(generatedPassword))
                .isActive(true)
                .customer(customer)
                .build());

        LOGGER.warn(String.format(LOG_CREATED_MSG, CUSTOMER_LABEL, customer.getName()));
        return customer;
    }
}
