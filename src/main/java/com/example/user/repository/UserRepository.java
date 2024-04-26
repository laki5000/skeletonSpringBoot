package com.example.user.repository;

import com.example.user.model.User;
import com.example.utils.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** A repository interface for managing users. */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * Find a user by username.
     *
     * @param username the username
     * @return an optional with the user, or empty if not found
     */
    Optional<User> findByUsername(String username);
}
