package com.ivanfrias.company.security.dto;

public record AuthenticationRequest(
        String email,
        String password
) {}

