package com.startup.oda.controller;

import com.startup.oda.dto.request.PasswordUpdateRequest;
import com.startup.oda.dto.request.ProfileUpdateRequest;
import com.startup.oda.service.ImageService;
import com.startup.oda.service.PasswordService;
import com.startup.oda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/user")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final PasswordService passwordService;
    private final UserService userService;
    private final ImageService imageService;

    public UserController(PasswordService passwordService, UserService userService, ImageService imageService) {
        this.passwordService = passwordService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @PutMapping(path = "/update-password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String email,
                                            @RequestBody PasswordUpdateRequest request){
        return passwordService.updatePassword(email, request);
    }
    @PatchMapping(path = "/update-profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal String email,
                                           @RequestBody ProfileUpdateRequest request){
        return ResponseEntity.ok(userService.updateProfile(email, request));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal String email){
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal String email){
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("User with email " + email + "is being deleted");
        } catch (RuntimeException e){
            LOGGER.error("Error during deleting user " + e);
            return ResponseEntity.badRequest().body("Something unexpected happened");
        }
    }
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@AuthenticationPrincipal String email,
                                         @RequestParam(name = "image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.uploadUserImage(email, file));
    }

    @GetMapping("/user-image")
    public ResponseEntity<?> getUserImage(@AuthenticationPrincipal String email){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageService.getUserImage(email));
    }
}
