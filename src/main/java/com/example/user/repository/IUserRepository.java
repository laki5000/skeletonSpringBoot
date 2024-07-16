package com.example.user.repository;

import com.example.user.model.User;
import com.example.utils.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

/** A repository interface for user-related operations. */
@Repository
public interface IUserRepository extends IBaseRepository<User, Long> {
}
