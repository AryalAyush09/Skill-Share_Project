package com.project.skill_share.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.User;


@Repository
  public interface UserRepository extends JpaRepository<User, Long> {
		boolean existsByUsername(String username);
		boolean existsByEmail(String email);
//		boolean existsByEmail(String email);
//	Optional<User> findByUsernameAndPassword(String username, String password);
		Optional<User> findByEmail(String email);
		Optional<User> findById(Long id);

	   @Query("SELECT u FROM User u WHERE u.id <> :userId")
	    List<User> findAllExcept(@Param("userId") Long userId);
//      	List<User> findAllById(Set<Long> userIds);
	}


