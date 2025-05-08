package com.ivanfrias.company.security.services;

import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import com.ivanfrias.company.security.dao.repositories.UserRepository;
import com.ivanfrias.company.security.dto.AuthenticationRequest;
import com.ivanfrias.company.security.dto.AuthenticationResponse;
import com.ivanfrias.company.security.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstname(request.firstname())
                .lastname(request.lastname())
                .isActive(true)
                .role(request.role())
                .build();
        userRepository.save(user);

        org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .accountLocked(!user.getIsActive())
                .build();

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var jwtToken = jwtService.generateToken(
                userRepository.findByEmail(request.email()).orElse(UserEntity.builder().build())
        );
        return new AuthenticationResponse(jwtToken);
    }
}

