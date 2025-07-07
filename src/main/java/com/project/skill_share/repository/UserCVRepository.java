package com.project.skill_share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.UserCV;

public interface UserCVRepository extends JpaRepository<UserCV, Long> {
    UserCV findByUserId(Long userId);
}
