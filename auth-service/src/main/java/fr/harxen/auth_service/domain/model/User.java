package fr.harxen.auth_service.domain.model;

import fr.harxen.auth_service.domain.model.enums.PlanType;
import fr.harxen.auth_service.domain.model.enums.Role;
import fr.harxen.auth_service.domain.model.valueobject.Email;

import java.time.Instant;
import java.util.Objects;

public class User {

    private Long id;
    private final Email email;
    private String passwordHash;
    private Role role;
    private PlanType plan;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;

    private User(Long id,
                 Email email,
                 String passwordHash,
                 Role role,
                 PlanType plan,
                 boolean enabled,
                 Instant createdAt,
                 Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.plan = plan;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User createNew(Email email, String passwordHash){
        Instant now = Instant.now();
        return new User(
                null,
                email,
                passwordHash,
                Role.USER,
                PlanType.FREE,
                true,
                now,
                now
        );
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public PlanType getPlan() {
        return plan;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() { return updatedAt;}

    public void changePlan(PlanType newPlan){
        this.plan = Objects.requireNonNull(newPlan, "Plan must not be null");
        this.updatedAt = Instant.now();
    }

    public void disable() {
        this.enabled = false;
        this.updatedAt = Instant.now();
    }

    public void enable() {
        this.enabled = true;
        this.updatedAt = Instant.now();
    }

    public void setId(Long id) {this.id = id;}
}
