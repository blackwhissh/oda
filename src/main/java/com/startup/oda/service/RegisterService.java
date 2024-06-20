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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final ClientRepository clientRepository;
    private final OwnerRepository ownerRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @Autowired
    public RegisterService(UserRepository userRepository,
                           AgentRepository agentRepository,
                           ClientRepository clientRepository,
                           OwnerRepository ownerRepository, RefreshTokenService refreshTokenService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.ownerRepository = ownerRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }
    public ResponseEntity<?> registerAgent(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Agent agent = new Agent();

        agent.setFirstName(request.getFirstName());
        agent.setLastName(request.getLastName());
        agent.setUser(user);

        agentRepository.save(agent);

//        String accessToken = jwtUtils.generateJwtToken(request.getEmail(), RoleEnum.valueOf(request.getRole().toUpperCase()));
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        String role = "ROLE_" + user.getRole().toString();
//        return ResponseEntity.ok(new JwtResponse(
//                accessToken,
//                refreshToken.getToken(),
//                user.getUserId(),
//                request.getEmail(),
//                role
//        ));
        return ResponseEntity.ok().body("agent registered successfully");
    }
    public ResponseEntity<?> registerClient(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Client client = new Client();

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setUser(user);
        clientRepository.save(client);

        String accessToken = jwtUtils.generateJwtToken(request.getEmail(), RoleEnum.valueOf(request.getRole().toUpperCase()));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        String role = "ROLE_" + user.getRole().toString();
        return ResponseEntity.ok(new JwtResponse(
                accessToken,
                refreshToken.getToken(),
                user.getUserId(),
                request.getEmail(),
                role
        ));
    }

    public ResponseEntity<?> registerOwner(RegisterRequest request){
        User user = createUser(request);
        if(user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }

        Owner owner = new Owner();

        owner.setFirstName(request.getFirstName());
        owner.setLastName(request.getLastName());
        owner.setUser(user);
        ownerRepository.save(owner);

        String accessToken = jwtUtils.generateJwtToken(request.getEmail(), RoleEnum.valueOf(request.getRole().toUpperCase()));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        String role = "ROLE_" + user.getRole().toString();
        return ResponseEntity.ok(new JwtResponse(
                accessToken,
                refreshToken.getToken(),
                user.getUserId(),
                request.getEmail(),
                role
        ));
    }

    private User createUser(RegisterRequest request) {
        if(userRepository.existsByEmailAndIsActive(request.getEmail(), true)){
            return null;
        }
        User user = new User();

        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(RoleEnum.valueOf(request.getRole().toUpperCase()));
        user.setActive(true);
        user.setVerified(true);
        userRepository.save(user);

        return user;
    }
}
