package com.stephanetoukam.stephnews.controller.api;

import com.stephanetoukam.stephnews.dao.request.SignUpRequest;
import com.stephanetoukam.stephnews.dao.request.SigninRequest;
import com.stephanetoukam.stephnews.dao.response.ApiCustomResponse;
import com.stephanetoukam.stephnews.dao.response.JwtAuthenticationResponse;
import com.stephanetoukam.stephnews.services.AuthenticationService;
import com.stephanetoukam.stephnews.services.EmailService;
import com.stephanetoukam.stephnews.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @Autowired
    private final StorageService storageService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiCustomResponse> signup(@ModelAttribute SignUpRequest request) {
        String fileCustomName = storageService.store(request.getAvatar());
        request.setAvatarFileName(fileCustomName);

        ApiCustomResponse response = authenticationService.signup(request);
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
