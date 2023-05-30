package dev.betul.repository;

import dev.betul.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, UUID> {
}
