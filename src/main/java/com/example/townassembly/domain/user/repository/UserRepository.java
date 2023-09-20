package com.example.townassembly.domain.user.repository;

import com.example.townassembly.domain.user.entity.User;
import com.example.townassembly.domain.user.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  List<User> findByRole(UserRoleEnum userRoleEnum);
  Optional<User> findByNickname(String nickname);
  Optional<User> findByEmail(String email);
  List<User> findByLocation(String location);
  List<User> findByParty(String party);
}
