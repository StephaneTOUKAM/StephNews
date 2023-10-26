package com.stephanetoukam.stephnews.controller.api;

import com.stephanetoukam.stephnews.dao.request.SignUpRequest;
import com.stephanetoukam.stephnews.dao.request.SigninRequest;
import com.stephanetoukam.stephnews.dao.response.ApiCustomResponse;
import com.stephanetoukam.stephnews.dao.response.JwtAuthenticationResponse;
import com.stephanetoukam.stephnews.services.AuthenticationService;
import com.stephanetoukam.stephnews.services.MailSenderService;
import com.stephanetoukam.stephnews.services.StorageService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final MailSenderService mailService;

    @Autowired
    private final StorageService storageService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiCustomResponse> signup(@ModelAttribute SignUpRequest request) throws MessagingException, TemplateException, IOException {
        if(Objects.nonNull(request.getAvatar())) {
            String fileCustomName = storageService.storeInCloud(request.getAvatar());
            request.setAvatarFileName(fileCustomName);
        }
        ApiCustomResponse response = authenticationService.signup(request);
        if (Objects.nonNull(response)) {
            mailService.sendNewMail(request.getEmail(), "Hello "+request.getLastName(), request);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
