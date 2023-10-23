package com.stephanetoukam.stephnews.controller.api;

import com.stephanetoukam.stephnews.dao.response.ApiCustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiCustomResponse> home() {
        ApiCustomResponse response = new ApiCustomResponse();
        response.setMessage("Hello, world!");
        response.setData("This is some custom data.");

        return ResponseEntity.ok(response);
    }
}
