package fr.harxen.auth_service.infrastructure.persistence.jpa.repository;


import fr.harxen.auth_service.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);

}
