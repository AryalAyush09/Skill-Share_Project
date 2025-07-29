package com.project.skill_share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.SocialLink;
import com.project.skill_share.entities.User;

import java.util.List;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {

    List<SocialLink> findByUserId(Long userId);

	void deleteByUser(User user);
}
