package com.startup.oda.service;

import com.startup.oda.config.LogEntryExit;
import com.startup.oda.dto.request.ProfileUpdateRequest;
import com.startup.oda.dto.response.ProfileUpdateResponse;
import com.startup.oda.dto.response.UserProfileDto;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.exception.exceptionsList.VerificationErrorException;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.security.jwt.RefreshTokenService;
import com.startup.oda.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public UserProfileDto getProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getVerified(), user.getBio());
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setDeletionDate(LocalDate.now().plusDays(30));
        user.setActive(false);
        userRepository.save(user);
        LOGGER.info("User with email {} is being deleted", email);
        refreshTokenService.deleteByUserId(user.getUserId());
    }

    @LogEntryExit
    @Scheduled(cron = "0 0 * * *")
    public void deleteUsersPermanently() {
        LocalDate now = LocalDate.now();
        List<User> usersToDelete = userRepository.findByIsActiveFalseAndDeletionDateBefore(now);
        userRepository.deleteAll(usersToDelete);
        LOGGER.info("Performed users permanent deletion");
    }

    public ProfileUpdateResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (Validation.isValidFirstName(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (Validation.isValidLastName(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (Validation.isValidInfo(request.getBio())) {
            user.setBio(request.getBio());
        }

        if (Validation.isValidPhone(request.getPhone())) {
            user.setPhone(request.getPhone());
        }

        return new ProfileUpdateResponse(user.getFirstName(), user.getLastName(), user.getPhone(), user.getBio());
    }

    public void verifyUser(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            user.setVerified(true);
            userRepository.save(user);
            LOGGER.info("User with email " + email + " set to verified");
        } catch (RuntimeException e) {
            LOGGER.error("Error while verifying user with email " + email);
            throw new VerificationErrorException();
        }
    }
}
