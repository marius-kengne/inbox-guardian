package fr.harxen.auth_service.infrastructure.persistence.jpa.adapter;

import fr.harxen.auth_service.domain.model.User;
import fr.harxen.auth_service.domain.model.valueobject.Email;
import fr.harxen.auth_service.domain.repository.UserRepository;
import fr.harxen.auth_service.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import fr.harxen.auth_service.infrastructure.persistence.jpa.repository.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email.value())
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var saved = springDataUserRepository.save(UserJpaMapper.toEntity(user));
        return UserJpaMapper.toDomain(saved);
    }
}
