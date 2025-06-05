package com.project.skill_share.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.User;


@Repository
  public interface UserRepository extends JpaRepository<User, Integer> {
		boolean existsByUsername(String username);
		boolean existsByEmail(String email);
//		boolean existsByEmail(String email);
//	Optional<User> findByUsernameAndPassword(String username, String password);
		Optional<User> findByUsername(String username);
		Optional<User> findByEmail(String email);
		Optional<User> findById(Long id);
	}


