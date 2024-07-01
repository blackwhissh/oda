package com.startup.oda.controller;

import com.startup.oda.config.LogEntryExit;
import com.startup.oda.dto.request.SignInRequest;
import com.startup.oda.dto.request.TokenRefreshRequest;
import com.startup.oda.dto.response.JwtResponse;
import com.startup.oda.dto.response.MessageResponse;
import com.startup.oda.dto.response.TokenRefreshResponse;
import com.startup.oda.entity.RefreshToken;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.RefreshTokenNotFoundException;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.security.ApplicationUser;
import com.startup.oda.security.jwt.JwtUtils;
import com.startup.oda.security.jwt.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }
    @PostMapping("/login")
    @LogEntryExit()
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest){
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);
        refreshTokenService.deleteByUserId(user.getUserId());
        if(!user.getActive()){
            return ResponseEntity.badRequest().body(new MessageResponse("User set to inactive, please contact support"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(applicationUser.getUsername(), user.getRole());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getAuthorities().stream().findFirst().get().toString()
        ));
    }
    @PostMapping("/refreshtoken")
    @LogEntryExit()
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getEmail(), user.getRole());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(RefreshTokenNotFoundException::new);
    }
    @PostMapping("/logout")
    @LogEntryExit()
    public ResponseEntity<?> logoutUser() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();
        refreshTokenService.deleteByUserId(userId);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
