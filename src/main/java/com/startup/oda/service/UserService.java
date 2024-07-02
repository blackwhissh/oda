package com.startup.oda.service;

import com.startup.oda.config.LogEntryExit;
import com.startup.oda.dto.response.UserProfileDto;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.security.jwt.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        return new UserProfileDto(
                userOptional.get().getFirstName(),
                userOptional.get().getLastName(),
                userOptional.get().getEmail(),
                userOptional.get().getVerified(),
                userOptional.get().getBio()
        );
    }
    public ResponseEntity<?> deleteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setDeletionDate(LocalDate.now().plusDays(30));
        user.setActive(false);
        userRepository.save(user);
        LOGGER.info("User with email {} is being deleted", email);
        refreshTokenService.deleteByUserId(user.getUserId());
        return ResponseEntity.ok("User with email " + email + "is being deleted");
    }
    @LogEntryExit
    @Scheduled(cron = "0 0 * * *")
    public void deleteUsersPermanently(){
        LocalDate now = LocalDate.now();
        List<User> usersToDelete = userRepository.findByIsActiveFalseAndDeletionDateBefore(now);
        userRepository.deleteAll(usersToDelete);
        LOGGER.info("Performed users permanent deletion");
    }
}
