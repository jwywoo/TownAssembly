package com.example.townassembly.domain.user.repository;

import com.example.townassembly.domain.user.entity.VoterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface VoterUserRepository extends JpaRepository<VoterUser, String> {

    Optional<VoterUser> findByVoterId(String voterId);

}
