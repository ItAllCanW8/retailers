package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.response.UserPageResp;
import com.itechart.retailers.service.exception.EmptyPasswordException;
import com.itechart.retailers.service.exception.IncorrectPasswordException;
import com.itechart.retailers.service.exception.RoleNotFoundException;

import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    User save(User user);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    User getById(Long id);

    /**
     * Update.
     *
     * @param user            the user
     * @param currentPassword the current password
     * @param newPassword     the new password
     * @throws IncorrectPasswordException the incorrect password exception
     * @throws EmptyPasswordException     the empty password exception
     */
    void update(User user, String currentPassword, String newPassword) throws IncorrectPasswordException, EmptyPasswordException;

    /**
     * Delete.
     *
     * @param user the user
     */
    void delete(User user);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    Optional<User> getByEmail(String email);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    Boolean existsByEmail(String email);

    /**
     * Gets by role and customer id.
     *
     * @param role       the role
     * @param customerId the customer id
     * @return the by role and customer id
     */
    User getByRoleAndCustomerId(Role role, Long customerId);

    /**
     * Change user status.
     *
     * @param customerId the customer id
     * @param status     the status
     */
    void changeUserStatus(Long customerId, boolean status);

    /**
     * Gets users.
     *
     * @param roleName the role name
     * @param page     the page
     * @return the users
     * @throws RoleNotFoundException the role not found exception
     */
    UserPageResp getUsers(String roleName, Integer page) throws RoleNotFoundException;

}
