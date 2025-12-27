package fr.harxen.auth_service.application.usecase;

import fr.harxen.auth_service.application.command.RegisterUserCommand;
import fr.harxen.auth_service.application.exception.EmailAlreadyUsedException;
import fr.harxen.auth_service.application.result.RegisterUserResult;
import fr.harxen.auth_service.domain.model.User;
import fr.harxen.auth_service.domain.model.valueobject.Email;
import fr.harxen.auth_service.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterUserResult execute(RegisterUserCommand command){
        Email email = Email.of(command.email());

        if (userRepository.findByEmail(email).isPresent()){
            throw new EmailAlreadyUsedException();
        }

        String hashed = passwordEncoder.encode(command.password());
        User user = User.createNew(email, hashed);
        User saved = userRepository.save(user);

        return new RegisterUserResult(saved.getId(), saved.getEmail().value());
    }
}
