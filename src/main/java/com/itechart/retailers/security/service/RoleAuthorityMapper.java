package com.itechart.retailers.security.service;

import lombok.Getter;

import java.util.List;

@Getter
public enum RoleAuthorityMapper {
    SYSTEM_ADMIN(List.of("ROLE_SYSTEM_ADMIN", "user:add", "location:post")),
    ADMIN(List.of("ROLE_ADMIN", "location:get", "item:get", "item:post", "location:post")),
    DISPATCHER(List.of("ROLE_DISPATCHER", "location:get", "item:get", "item:post", "application:get", "application:post")),
    WAREHOUSE_MANAGER(List.of("ROLE_WAREHOUSE_MANAGER", "application:get", "application:post")),
    SHOP_MANAGER(List.of("ROLE_SHOP_MANAGER", "application:get", "application:post")),
    DIRECTOR(List.of("ROLE_DIRECTOR"));

    private final List<String> authorities;

    RoleAuthorityMapper(List<String> authorities) {
        this.authorities = authorities;
    }
}
