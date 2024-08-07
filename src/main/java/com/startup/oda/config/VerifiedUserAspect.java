package com.startup.oda.config;

import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.exception.exceptionsList.UserNotVerifiedException;
import com.startup.oda.exception.exceptionsList.VerificationErrorException;
import com.startup.oda.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VerifiedUserAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifiedUserAspect.class);
    private final UserRepository userRepository;

    public VerifiedUserAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("@annotation(VerifiedUser)")
    public void checkVerifiedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (!user.getVerified()) {
            LOGGER.warn("User is not verified, no access to perform this action");
            throw new UserNotVerifiedException();
        }
    }

}
