package fr.harxen.auth_service.api.controller;

import fr.harxen.auth_service.api.dto.RegisterRequest;
import fr.harxen.auth_service.api.dto.RegisterResponse;
import fr.harxen.auth_service.application.command.RegisterUserCommand;
import fr.harxen.auth_service.application.result.RegisterUserResult;
import fr.harxen.auth_service.application.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase){
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        RegisterUserResult result = registerUserUseCase.execute(
                new RegisterUserCommand(request.email(), request.password())
        );
        return new RegisterResponse(result.userId(), request.email());
    }
}
