package com.project.skill_share.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.skill_share.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{
	boolean existsBySessionIdAndRaterUserId(Long sessionId);

	List<Rating> findByRateeUserId(Long userId);
	
	Page<Rating> findByRateeUserIdOrderByCreatedAtDesc(Long rateeUserId, Pageable pageable);

}
