package com.ivanfrias.company.security.dto;


import com.ivanfrias.company.security.dao.models.enums.RoleEnum;

public record RegisterRequest(
        String email,
        String password,
        String firstname,
        String lastname,
        RoleEnum role
) {}

