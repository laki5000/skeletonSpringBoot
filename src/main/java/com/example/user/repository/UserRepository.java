package com.example.user.repository;

import com.example.user.model.User;
import com.example.utils.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/** A repository interface for managing users. */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * Find out if a user exists by username.
     *
     * @param username the username
     * @return true if a user exists by username, false otherwise
     */
    boolean existsByUsername(String username);
}
