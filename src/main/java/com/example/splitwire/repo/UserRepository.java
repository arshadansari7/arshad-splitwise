package com.example.splitwire.repo;

import com.example.splitwire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    void deleteById(Integer id);
}
