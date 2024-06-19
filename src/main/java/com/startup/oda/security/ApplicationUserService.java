package com.startup.oda.security;

import com.startup.oda.entity.User;
import com.startup.oda.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ApplicationUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;

        user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        if (user != null) {
            return new ApplicationUser(
                    user.getUserId(), user.getEmail(), user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }
        throw new UsernameNotFoundException(String.format("Email %s not found", email));
    }
}
