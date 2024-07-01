package com.startup.oda.controller;

import com.startup.oda.dto.request.PasswordUpdateRequest;
import com.startup.oda.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/user")
public class UserController {
    private final PasswordService passwordService;

    public UserController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PutMapping(path = "/update-password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String email,
                                            @RequestBody PasswordUpdateRequest request){
        return passwordService.updatePassword(email, request);
    }
}
