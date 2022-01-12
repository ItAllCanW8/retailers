package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Role;

/**
 * The interface Role service.
 */
public interface RoleService {

    /**
     * Save role.
     *
     * @param roleName the role name
     * @return the role
     */
    Role save(String roleName);

    /**
     * Gets by role.
     *
     * @param role the role
     * @return the by role
     */
    Role getByRole(String role);
}
