package com.startup.oda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("this is test");
    }
}
