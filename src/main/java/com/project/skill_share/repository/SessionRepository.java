package com.project.skill_share.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.skill_share.entities.MatchUser;
import com.project.skill_share.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
	 Optional<Session> findById(Long userId);
	  boolean existsByMatch(MatchUser match);
}
