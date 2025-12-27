package fr.harxen.auth_service.domain.model.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String value;

    private Email(String value){
        this.value = value;
    }

    public static Email of(String raw) {
        if (raw == null) throw new IllegalArgumentException("Email is required");
        String trimmed = raw.trim().toLowerCase();
        if (trimmed.isBlank()) throw new IllegalArgumentException("Email is required");
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new Email(trimmed);
    }

    public String value(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
