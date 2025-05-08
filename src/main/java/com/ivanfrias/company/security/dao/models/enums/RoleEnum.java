package com.ivanfrias.company.security.dao.models.enums;

public enum RoleEnum {
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
