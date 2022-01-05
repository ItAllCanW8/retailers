package com.itechart.retailers.security.service;

import lombok.Getter;

import java.util.List;

import static com.itechart.retailers.security.constant.Authority.*;

@Getter
public enum RoleAuthorityMapper {
    SYSTEM_ADMIN(List.of(
            SYSTEM_ADMIN_AUTHORITY,
            SYSTEM_ADMIN_ROLE,
            DEFAULT_AUTHORITY,
            USER_GET_AUTHORITY,
            USER_POST_AUTHORITY,
            LOCATION_POST_AUTHORITY
    )),
    ADMIN(List.of(
            ADMIN_AUTHORITY,
            ADMIN_ROLE,
            USER_GET_AUTHORITY,
            USER_POST_AUTHORITY,
            DEFAULT_AUTHORITY,
            LOCATION_GET_AUTHORITY,
            ITEM_GET_AUTHORITY,
            ITEM_POST_AUTHORITY,
            LOCATION_POST_AUTHORITY
    )),
    DISPATCHER(List.of(
            DISPATCHER_AUTHORITY,
            DISPATCHER_ROLE,
            DEFAULT_AUTHORITY,
            LOCATION_GET_AUTHORITY,
            USER_GET_AUTHORITY,
            ITEM_GET_AUTHORITY,
            ITEM_POST_AUTHORITY,
            APPLICATION_GET_AUTHORITY,
            APPLICATION_POST_AUTHORITY
    )),
    WAREHOUSE_MANAGER(List.of(
            WAREHOUSE_MANAGER_AUTHORITY,
            WAREHOUSE_MANAGER_ROLE,
            DEFAULT_AUTHORITY,
            LOCATION_GET_AUTHORITY,
            APPLICATION_GET_AUTHORITY,
            APPLICATION_POST_AUTHORITY
    )),
    SHOP_MANAGER(List.of(
            SHOP_MANAGER_AUTHORITY,
            SHOP_MANAGER_ROLE,
            DEFAULT_AUTHORITY,
            LOCATION_GET_AUTHORITY,
            APPLICATION_GET_AUTHORITY,
            BILL_GET_AUTHORITY,
            BILL_POST_AUTHORITY,
            WRITE_OFF_ACT_GET_AUTHORITY,
            WRITE_OFF_ACT_POST_AUTHORITY
    )),
    DIRECTOR(List.of(
            DIRECTOR_AUTHORITY,
            DIRECTOR_ROLE,
            DEFAULT_AUTHORITY,
            LOCATION_GET_AUTHORITY,
            WRITE_OFF_ACT_GET_AUTHORITY
    ));

    private final List<String> authorities;

    RoleAuthorityMapper(List<String> authorities) {
        this.authorities = authorities;
    }
}
