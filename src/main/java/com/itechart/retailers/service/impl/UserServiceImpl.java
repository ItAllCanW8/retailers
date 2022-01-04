package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.EmptyPasswordException;
import com.itechart.retailers.service.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextService securityService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void update(User user, String enteredCurrentPassword, String newPassword) throws IncorrectPasswordException, EmptyPasswordException {
        String actualPassword = userRepository.getById(user.getId()).getPassword();

        if (enteredCurrentPassword != null && !enteredCurrentPassword.equals("")) {
            if (newPassword == null || newPassword.equals("")) {
                throw new EmptyPasswordException();
            }
            if (!passwordEncoder.matches(enteredCurrentPassword, actualPassword)) {
                throw new IncorrectPasswordException();
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            if (newPassword != null && !newPassword.equals("")) {
                throw new IncorrectPasswordException();
            }
            user.setPassword(actualPassword);
        }
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findUsersByCustomerId(Long customerId) {
        return userRepository.findUsersByCustomerId(customerId);
    }

    @Override
    public User getByRoleAndCustomerId(Role role, Long customerId) {
        return userRepository.getByRoleAndCustomerId(role, customerId);
    }

    @Override
    public void changeUserStatus(Long customerId, boolean status) {
        Customer customer = customerRepository.getById(customerId);
        customer.setActive(status);
        customerRepository.save(customer);
        if (status) {
            Role role = roleRepository.getByRole("ADMIN");
            User user = userRepository.getByRoleAndCustomerId(role, customerId);
            user.setActive(true);
            userRepository.save(user);
        } else {
            List<User> customerUsers = userRepository.findUsersByCustomerIdAndActive(customerId, true);
            customerUsers.forEach(user -> user.setActive(false));
            userRepository.saveAll(customerUsers);
        }
    }

    @Override
    public List<User> getUsersByRoleNameInCurrentCustomer(String roleName) {
        Role role = roleRepository.findByRole(roleName).get();
        return userRepository.findUsersByRoleAndCustomer(role, securityService.getCurrentCustomer());
    }
}
