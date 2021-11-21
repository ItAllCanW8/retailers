package com.example.demo.model;

public enum Permission {
    READ("item:read"),
    WRITE("item:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
