package com.example.townassembly.domain.user.repository;

import com.example.townassembly.domain.user.entity.PoliticianUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliticianUserRepository extends JpaRepository<PoliticianUser, Long> {

    Optional<PoliticianUser> findByUsername(String username);

}
