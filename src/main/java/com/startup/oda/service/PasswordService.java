package com.startup.oda.service;

import com.startup.oda.config.PasswordConfig;
import com.startup.oda.dto.request.PasswordUpdateRequest;
import com.startup.oda.dto.response.MessageResponse;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.InvalidInputException;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordService.class);
    private final UserRepository userRepository;
    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> updatePassword(String email, PasswordUpdateRequest request) {
        if (!Validation.isValidPassword(request.getNewPassword())){
            LOGGER.error("New password is invalid");
            throw new InvalidInputException();
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (request.getNewPassword().equals(request.getOldPassword())) {
            LOGGER.error("New and old password can not be same");
            throw new InvalidInputException();
        }

        if (!BCrypt.checkpw(request.getOldPassword(), user.getPassword())) {
            LOGGER.error("Old password is wrong");
            throw new InvalidInputException();
        }


        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
    }
}
