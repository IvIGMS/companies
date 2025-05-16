package com.ivanfrias.company.security.services;

import com.ivanfrias.companies.model.AuthenticationDTO;
import com.ivanfrias.companies.model.AuthenticationRequestDTO;
import com.ivanfrias.companies.model.RegisterRequestDTO;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import com.ivanfrias.company.security.dao.models.enums.RoleEnum;
import com.ivanfrias.company.security.dao.repositories.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public AuthenticationDTO register(RegisterRequestDTO request) {
        var user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .isActive(false)
                .role(RoleEnum.USER)
                .build();
        userRepository.save(user);

        org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .accountLocked(!user.getIsActive())
                .build();

        return AuthenticationDTO.builder()
                .token(jwtService.generateToken(user))
                .build();
    }


    public AuthenticationDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(
                userRepository.findByEmail(request.getEmail()).orElse(UserEntity.builder().build())
        );
        return AuthenticationDTO.builder()
                .token(jwtToken)
                .build();
    }
}

