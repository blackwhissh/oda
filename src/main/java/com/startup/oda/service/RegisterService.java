package com.startup.oda.service;


import com.startup.oda.config.PasswordConfig;
import com.startup.oda.dto.request.RegisterRequest;
import com.startup.oda.dto.response.JwtResponse;
import com.startup.oda.dto.response.MessageResponse;
import com.startup.oda.entity.*;
import com.startup.oda.entity.enums.RoleEnum;
import com.startup.oda.repository.AgentRepository;
import com.startup.oda.repository.ClientRepository;
import com.startup.oda.repository.OwnerRepository;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.security.jwt.JwtUtils;
import com.startup.oda.security.jwt.RefreshTokenService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {
    private final static Logger log = LoggerFactory.getLogger(RegisterService.class);
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final ClientRepository clientRepository;
    private final OwnerRepository ownerRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final MailService mailService;

    @Autowired
    public RegisterService(UserRepository userRepository,
                           AgentRepository agentRepository,
                           ClientRepository clientRepository,
                           OwnerRepository ownerRepository, RefreshTokenService refreshTokenService, JwtUtils jwtUtils, MailService mailService) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.ownerRepository = ownerRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
    }
    public ResponseEntity<?> registerAgent(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Agent agent = new Agent();

        agent.setUser(user);

        agentRepository.save(agent);

        try {
            JwtResponse jwt = createJwt(request.getEmail(), request.getRole(), user.getUserId());
            log.info("Created JWT for Agent");
//            mailService.sendMail(request.getEmail(), jwt.getAccessToken());
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            log.error("Error creating JWT for Agent");
            throw new JwtException(e.getMessage());
        }
    }
    public ResponseEntity<?> registerClient(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Client client = new Client();
        client.setUser(user);
        clientRepository.save(client);

        try {
            JwtResponse jwt = createJwt(request.getEmail(), request.getRole(), user.getUserId());
            log.info("Created JWT for Client");
//            mailService.sendMail(request.getEmail(), jwt.getAccessToken());
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            log.error("Error creating JWT for Client");
            throw new JwtException(e.getMessage());
        }
    }

    public ResponseEntity<?> registerOwner(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Owner owner = new Owner();
        owner.setUser(user);
        ownerRepository.save(owner);

        try {
            JwtResponse jwt = createJwt(request.getEmail(), request.getRole(), user.getUserId());
            log.info("Created JWT for Owner");
//            mailService.sendMail(request.getEmail(), jwt.getAccessToken());
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            log.error("Error creating JWT for Owner");
            throw new JwtException(e.getMessage());
        }
    }

    private User createUser(RegisterRequest request) {
        if(userRepository.existsByEmailAndIsActive(request.getEmail(), true)){
            return null;
        }
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(RoleEnum.valueOf(request.getRole().toUpperCase()));
        user.setActive(true);
        user.setVerified(true);
        userRepository.save(user);

        return user;
    }

    private JwtResponse createJwt(String email, String role, Long userId){
        String accessToken = jwtUtils.generateJwtToken(email, RoleEnum.valueOf(role.toUpperCase()));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);
        String userRole = "ROLE_" + role.toUpperCase();
        return new JwtResponse(
                accessToken,
                refreshToken.getToken(),
                userId,
                email,
                userRole
        );
    }
}
