package com.startup.oda.controller;

import com.startup.oda.dto.request.PasswordUpdateRequest;
import com.startup.oda.service.PasswordService;
import com.startup.oda.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/user")
public class UserController {
    private final PasswordService passwordService;
    private final UserService userService;

    public UserController(PasswordService passwordService, UserService userService) {
        this.passwordService = passwordService;
        this.userService = userService;
    }

    @PutMapping(path = "/update-password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String email,
                                            @RequestBody PasswordUpdateRequest request){
        return passwordService.updatePassword(email, request);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal String email){
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal String email){
        return ResponseEntity.ok(userService.deleteUser(email));
    }
}
