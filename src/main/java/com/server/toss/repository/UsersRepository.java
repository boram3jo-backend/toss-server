package com.server.toss.repository;

import com.server.toss.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Long countByEmail(String email);
    Optional<Users> findByEmail(String email);
}
