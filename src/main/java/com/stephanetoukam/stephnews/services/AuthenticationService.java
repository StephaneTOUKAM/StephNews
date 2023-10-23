package com.stephanetoukam.stephnews.services;

import com.stephanetoukam.stephnews.dao.request.SignUpRequest;
import com.stephanetoukam.stephnews.dao.request.SigninRequest;
import com.stephanetoukam.stephnews.dao.response.ApiCustomResponse;
import com.stephanetoukam.stephnews.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    ApiCustomResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
