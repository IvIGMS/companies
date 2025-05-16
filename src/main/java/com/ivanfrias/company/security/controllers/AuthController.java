package com.ivanfrias.company.security.controllers;

import com.ivanfrias.companies.api.LoginApi;
import com.ivanfrias.companies.api.RegisterApi;
import com.ivanfrias.companies.model.AuthenticationDTO;
import com.ivanfrias.companies.model.AuthenticationRequestDTO;
import com.ivanfrias.companies.model.RegisterRequestDTO;
import com.ivanfrias.company.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements RegisterApi, LoginApi {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationDTO> login(AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @Override
    public ResponseEntity<AuthenticationDTO> register(RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authenticationService.register(registerRequestDTO));

    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return RegisterApi.super.getRequest();
    }
}
