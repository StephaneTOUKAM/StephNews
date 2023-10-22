package com.training.stephnews.controller.api;

import com.training.stephnews.dao.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<CustomResponse> home() {
        CustomResponse response = new CustomResponse();
        response.setMessage("Hello, world!");
        response.setData("This is some custom data.");

        return ResponseEntity.ok(response);
    }
}
