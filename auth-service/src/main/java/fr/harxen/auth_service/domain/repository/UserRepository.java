package fr.harxen.auth_service.domain.repository;

import fr.harxen.auth_service.domain.model.User;
import fr.harxen.auth_service.domain.model.valueobject.Email;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(Email email);
    User save(User user);
}
