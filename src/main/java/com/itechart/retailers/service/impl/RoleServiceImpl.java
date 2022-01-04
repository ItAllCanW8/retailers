package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(String roleName) {
        return roleRepository.findByRole(roleName).orElseGet(() ->
                roleRepository.save(Role.builder().role(roleName).build())
        );
    }

    @Override
    public Role getByRole(String role) {
        return roleRepository.getByRole(role);
    }

}
