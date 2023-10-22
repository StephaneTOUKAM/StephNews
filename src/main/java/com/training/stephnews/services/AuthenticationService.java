package com.training.stephnews.services;

import com.training.stephnews.dao.request.SignUpRequest;
import com.training.stephnews.dao.request.SigninRequest;
import com.training.stephnews.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
