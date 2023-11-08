package com.stephanetoukam.stephnews.services.impl;

import com.stephanetoukam.stephnews.dao.request.ResetPwdRequest;
import com.stephanetoukam.stephnews.dao.request.SignUpRequest;
import com.stephanetoukam.stephnews.dao.request.SigninRequest;
import com.stephanetoukam.stephnews.dao.response.ApiCustomResponse;
import com.stephanetoukam.stephnews.models.User;
import com.stephanetoukam.stephnews.models.enums.Role;
import com.stephanetoukam.stephnews.repositories.UserRepository;
import com.stephanetoukam.stephnews.services.AuthenticationService;
import com.stephanetoukam.stephnews.dao.response.JwtAuthenticationResponse;
import com.stephanetoukam.stephnews.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiCustomResponse signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .avatar(request.getAvatarFileName())
                .active(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return ApiCustomResponse.builder()
                .data(user)
                .message("Utilisateur cree avec succes")
                .build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public ApiCustomResponse forgotPassword(SigninRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        return ApiCustomResponse.builder()
                .data(user)
                .message("Un email vous a été envoyé pour verifier votre compte")
                .build();
    }

    @Override
    public ApiCustomResponse resetPassword(ResetPwdRequest request) {
        var user = userRepository.findByEmail(request.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        return ApiCustomResponse.builder()
                .data(user)
                .message("Un email vous a été envoyé pour verifier votre compte")
                .build();
    }
}
