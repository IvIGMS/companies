package com.ivanfrias.company.security.controllers;

import com.ivanfrias.companies.api.UsersApi;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.security.dto.AuthenticationRequest;
import com.ivanfrias.company.security.dto.AuthenticationResponse;
import com.ivanfrias.company.security.dto.RegisterRequest;
import com.ivanfrias.company.security.services.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
