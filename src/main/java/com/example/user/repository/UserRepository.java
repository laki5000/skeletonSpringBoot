package com.example.user.repository;

import com.example.user.model.User;
import com.example.utils.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/** A repository interface for managing users. */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
