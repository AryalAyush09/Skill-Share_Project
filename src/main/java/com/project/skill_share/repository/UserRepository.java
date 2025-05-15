package com.project.skill_share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.User;


@Repository
  public interface UserRepository extends JpaRepository<User, Integer> {
		boolean existsByUsername(String username);
		boolean existsByEmail(String email);
	}


