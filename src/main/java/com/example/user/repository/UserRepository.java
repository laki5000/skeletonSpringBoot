package com.example.user.repository;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** A repository interface for managing users. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Check if a user exists by username.
     *
     * @param username the username
     * @return true if a user exists by username, false otherwise
     */
    boolean existsByUsername(String username);
}
