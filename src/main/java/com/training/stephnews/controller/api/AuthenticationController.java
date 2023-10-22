package com.training.stephnews.controller.api;

import com.training.stephnews.dao.request.SignUpRequest;
import com.training.stephnews.dao.request.SigninRequest;
import com.training.stephnews.dao.response.JwtAuthenticationResponse;
import com.training.stephnews.services.AuthenticationService;
import com.training.stephnews.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        JwtAuthenticationResponse response = authenticationService.signup(request);
//        if (StringUtils.isNotBlank(response.getToken())) {
//            emailService.sendEmail(request.getEmail(), "Bienvenue "+request.getLastName(), "Bienvenue sur notre site");
//        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
