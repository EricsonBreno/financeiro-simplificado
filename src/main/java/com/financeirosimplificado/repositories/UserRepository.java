package com.financeirosimplificado.repositories;

import com.financeirosimplificado.domain.user.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByDocument(String document);
}
