package fr.harxen.auth_service.infrastructure.persistence.jpa.mapper;

import fr.harxen.auth_service.domain.model.User;
import fr.harxen.auth_service.domain.model.enums.PlanType;
import fr.harxen.auth_service.domain.model.enums.Role;
import fr.harxen.auth_service.domain.model.valueobject.Email;
import fr.harxen.auth_service.infrastructure.persistence.jpa.entity.UserJpaEntity;

import java.time.Instant;

public class UserJpaMapper {

    private UserJpaMapper() {}

    public static User toDomain(UserJpaEntity entity) {
        return reconstructDomainUser(
                entity.getId(),
                Email.of(entity.getEmail()),
                entity.getPasswordHash(),
                Role.valueOf(entity.getRole()),
                PlanType.FREE,
                entity.isEnabled(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserJpaEntity toEntity(User domain) {
        UserJpaEntity e = new UserJpaEntity();
        e.setId(domain.getId());
        e.setEmail(domain.getEmail().value());
        e.setPasswordHash(domain.getPasswordHash());
        e.setRole(domain.getRole().name());
        e.setEnabled(domain.isEnabled());

        Instant created = domain.getCreatedAt() != null ? domain.getCreatedAt() : Instant.now();
        Instant updated = domain.getUpdatedAt() != null ? domain.getUpdatedAt() : Instant.now();
        e.setCreatedAt(created);
        e.setUpdatedAt(updated);

        return e;
    }

    private static User reconstructDomainUser(Long id,
                                              Email email,
                                              String passwordHash,
                                              Role role,
                                              PlanType plan,
                                              boolean enabled,
                                              Instant createdAt,
                                              Instant updatedAt) {
        return User.reconstruct(id, email, passwordHash, role, plan, enabled, createdAt, updatedAt);
    }
}
