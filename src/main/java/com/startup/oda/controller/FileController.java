package com.startup.oda.controller;

import com.startup.oda.config.LogEntryExit;
import com.startup.oda.dto.response.ProfileImageDataDto;
import com.startup.oda.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @LogEntryExit
    @PostMapping("/upload-profile-image")
    public ResponseEntity<?> uploadFile(@AuthenticationPrincipal String email,
                                        @RequestParam("profile-image")MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadProfileImage(email, file));
    }
    @LogEntryExit
    @GetMapping("/profile-image")
    public ResponseEntity<?> getFile(@AuthenticationPrincipal String email) throws IOException {
        ProfileImageDataDto profileImage = fileService.getProfileImage(email);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(profileImage.getType()))
                .body(profileImage.getImageData());
    }
}
