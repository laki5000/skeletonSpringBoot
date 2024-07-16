package com.example.user.repository;

import com.example.user.model.User;
import com.example.utils.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

/** A repository interface for user-related operations. */
@Repository
public interface IUserRepository extends IBaseRepository<User, Long> {
  /**
   * Checks if a user with the given username exists.
   *
   * @param username the username to check
   * @return true if a user with the given username exists, false otherwise
   */
  boolean existsByUsername(String username);
}
