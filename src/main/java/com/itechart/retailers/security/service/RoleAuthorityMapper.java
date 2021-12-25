package com.itechart.retailers.security.service;

import lombok.Getter;

import java.util.List;

@Getter
public enum RoleAuthorityMapper {
    SYSTEM_ADMIN(List.of(
            "ROLE_SYSTEM_ADMIN",
            "SYSTEM_ADMIN",
            "any",
            "user:add",
            "location:post"
    )),
    ADMIN(List.of(
            "ROLE_ADMIN",
            "ADMIN",
            "any",
            "location:get",
            "item:get",
            "item:post",
            "location:post"
    )),
    DISPATCHER(List.of(
            "ROLE_DISPATCHER",
            "DISPATCHER",
            "any",
            "location:get",
            "item:get",
            "item:post",
            "application:get",
            "application:post"
    )),
    WAREHOUSE_MANAGER(List.of(
            "ROLE_WAREHOUSE_MANAGER",
            "WAREHOUSE_MANAGER",
            "any",
            "location:get",
            "application:get",
            "application:post"
    )),
    SHOP_MANAGER(List.of(
            "ROLE_SHOP_MANAGER",
            "SHOP_MANAGER",
            "any",
            "location:get",
            "application:get",
            "bill:get",
            "bill:post"
    )),
    DIRECTOR(List.of(
            "ROLE_DIRECTOR",
            "DIRECTOR",
            "any"
    ));

    private final List<String> authorities;

    RoleAuthorityMapper(List<String> authorities) {
        this.authorities = authorities;
    }
}
