package com.stephanetoukam.stephnews.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<HashMap> home() {
        // get a successful user login
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(new HashMap(){{
            put("hello", user.getAttribute("name"));
            put("your email is", user.getAttribute("email"));
        }});
    }


    @GetMapping("/unauthenticated")
    public ResponseEntity<HashMap> unauthenticatedRequests() {
        return ResponseEntity.ok(new HashMap(){{
            put("this is ", "unauthenticated endpoint");
        }});
    }
}
