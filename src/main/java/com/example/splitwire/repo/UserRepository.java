package com.example.splitwire.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<com.example.splitwire.entity.User, Integer> {
}
