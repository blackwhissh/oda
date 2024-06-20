package com.startup.oda.service;


import com.startup.oda.config.PasswordConfig;
import com.startup.oda.dto.request.RegisterRequest;
import com.startup.oda.dto.response.MessageResponse;
import com.startup.oda.entity.Agent;
import com.startup.oda.entity.Client;
import com.startup.oda.entity.Owner;
import com.startup.oda.entity.User;
import com.startup.oda.entity.enums.RoleEnum;
import com.startup.oda.repository.AgentRepository;
import com.startup.oda.repository.ClientRepository;
import com.startup.oda.repository.OwnerRepository;
import com.startup.oda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final ClientRepository clientRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public RegisterService(UserRepository userRepository,
                           AgentRepository agentRepository,
                           ClientRepository clientRepository,
                           OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.ownerRepository = ownerRepository;
    }
    public ResponseEntity<?> registerAgent(RegisterRequest request){
        if(userRepository.existsByEmailAndIsActive(request.getEmail(), true)){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }
        User user = new User();

        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(RoleEnum.AGENT);
        user.setActive(true);
        user.setVerified(true);
        userRepository.save(user);

        Agent agent = new Agent();

        agent.setFirstName(request.getFirstName());
        agent.setLastName(request.getLastName());
        agent.setUser(user);

        agentRepository.save(agent);

        return ResponseEntity.ok().body(new MessageResponse("Agent registered successfully!"));
    }
    public ResponseEntity<?> registerClient(RegisterRequest request){
        if(userRepository.existsByEmailAndIsActive(request.getEmail(), true)){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }
        User user = new User();

        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(RoleEnum.CLIENT);
        user.setActive(true);
        user.setVerified(true);
        userRepository.save(user);

        Client client = new Client();

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setUser(user);
        clientRepository.save(client);

        return ResponseEntity.ok().body(new MessageResponse("Client registered successfully!"));
    }

    public ResponseEntity<?> registerOwner(RegisterRequest request){
        if(userRepository.existsByEmailAndIsActive(request.getEmail(), true)){
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already registered!"));
        }
        User user = new User();

        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(RoleEnum.OWNER);
        user.setActive(true);
        user.setVerified(true);
        userRepository.save(user);

        Owner owner = new Owner();

        owner.setFirstName(request.getFirstName());
        owner.setLastName(request.getLastName());
        owner.setUser(user);
        ownerRepository.save(owner);

        return ResponseEntity.ok().body(new MessageResponse("Owner registered successfully!"));
    }
}
