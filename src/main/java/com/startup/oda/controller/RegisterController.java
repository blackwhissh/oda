package com.startup.oda.controller;

import com.startup.oda.dto.request.RegisterRequest;
import com.startup.oda.entity.enums.RoleEnum;
import com.startup.oda.exception.exceptionsList.InvalidInputException;
import com.startup.oda.exception.exceptionsList.WrongRoleException;
import com.startup.oda.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.startup.oda.utils.Validation.validateRegisterRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/api/register")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/client")
    public ResponseEntity<?> registerClient(@RequestBody RegisterRequest registerRequest){
        if (!validateRegisterRequest(registerRequest)){
            throw new InvalidInputException();
        }
        return registerService.registerClient(registerRequest);
    }
    @PostMapping("/owner")
    public ResponseEntity<?> registerOwner(@RequestBody RegisterRequest registerRequest){
        if (!validateRegisterRequest(registerRequest)){
            throw new InvalidInputException();
        }
        return registerService.registerOwner(registerRequest);
    }
    @PostMapping("/agent")
    public ResponseEntity<?> registerAgent(@RequestBody RegisterRequest registerRequest){
        if (!validateRegisterRequest(registerRequest)){
            throw new InvalidInputException();
        }
        return registerService.registerAgent(registerRequest);
    }
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        if (!validateRegisterRequest(registerRequest)){
            throw new InvalidInputException();
        }
        RoleEnum role = registerRequest.getRoleEnum();
        switch (role){
            case AGENT -> {
                return registerService.registerAgent(registerRequest);
            }
            case OWNER -> {
                return registerService.registerOwner(registerRequest);
            }
            case CLIENT -> {
                return registerService.registerClient(registerRequest);
            }
            default -> throw new WrongRoleException();
        }
    }
}
