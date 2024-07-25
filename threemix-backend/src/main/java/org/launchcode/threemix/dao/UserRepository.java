package org.launchcode.threemix.dao;

import org.launchcode.threemix.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByUserId(String userId);
}
