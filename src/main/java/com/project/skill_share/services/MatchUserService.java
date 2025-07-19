package com.project.skill_share.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.Set;

import com.project.skill_share.DTO.MatchResultDto;
import com.project.skill_share.DTO.MatchSkillDto;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.User_Skill;
import com.project.skill_share.enums.SkillType;
import com.project.skill_share.repository.SkillRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.repository.UserSkillRepository;
import com.project.skill_share.response.ApiResponse;

@Service
public class MatchUserService {

	private final UserRepository userRepo;
	private final UserSkillRepository userSkillRepo;
	private final SkillRepository skillRepo;

	public MatchUserService(UserRepository userRepo, UserSkillRepository userSkillRepo, SkillRepository skillRepo) {
		this.userRepo = userRepo;
		this.userSkillRepo = userSkillRepo;
		this.skillRepo = skillRepo;
	}

	public ApiResponse<?> matchingUser(Long currentUserId) {

		User currentUser = getUserById(currentUserId);

		// Get current user skills (have and need)
		List<User_Skill> currentUserHaveSkills = userSkillRepo.findByUserAndType(currentUser, SkillType.HAVE);
		List<User_Skill> currentUserNeedSkills = userSkillRepo.findByUserAndType(currentUser, SkillType.NEED);

		// Extract categories from current user skills
		Set<Long> currentHaveCategoriesId = extractCategoryIds(currentUserHaveSkills);
		Set<Long> currentNeedCategoriesId = extractCategoryIds(currentUserNeedSkills);

		// Get all other users except current
		List<User> allOtherUser = userRepo.findAllExcept(currentUserId);

		List<MatchResultDto> matchedUsers = new ArrayList<>();

		for (User otherUser : allOtherUser) {

			// Get other user skills
			List<User_Skill> otherHaveSkills = userSkillRepo.findByUserAndType(otherUser, SkillType.HAVE);
			List<User_Skill> otherNeedSkills = userSkillRepo.findByUserAndType(otherUser, SkillType.NEED);

			// Extract categories for other user
			Set<Long> otherHaveCategoriesId = extractCategoryIds(otherHaveSkills);
			Set<Long> otherNeedCategoriesId = extractCategoryIds(otherNeedSkills);

			// Check category-level match: currentUserHave vs otherUserNeed and currentUserNeed vs otherUserHave
			Set<Long> matchedGiveCategories = intersection(currentHaveCategoriesId, otherNeedCategoriesId);
			Set<Long> matchedTakeCategories = intersection(currentNeedCategoriesId, otherHaveCategoriesId);

			// Proceed only if categories match both ways
			if (!matchedGiveCategories.isEmpty() && !matchedTakeCategories.isEmpty()) {

				// Filter skills by matched categories
				Set<Long> currentGiveSkills = filterSkillIdsByCategories(currentUserHaveSkills, matchedGiveCategories);
				Set<Long> currentTakeSkills = filterSkillIdsByCategories(currentUserNeedSkills, matchedTakeCategories);

				Set<Long> otherNeedSkillsInCategory = filterSkillIdsByCategories(otherNeedSkills, matchedGiveCategories);
				Set<Long> otherHaveSkillsInCategory = filterSkillIdsByCategories(otherHaveSkills, matchedTakeCategories);

				// Find skill intersections (skills that mutually match)
				Set<Long> matchedGiveSkills = intersection(currentGiveSkills, otherNeedSkillsInCategory);
				Set<Long> matchedTakeSkills = intersection(currentTakeSkills, otherHaveSkillsInCategory);

				// Only consider if both sides have some matched skills
				if (!matchedGiveSkills.isEmpty() && !matchedTakeSkills.isEmpty()) {

					int matchedTeach = matchedGiveSkills.size();
					int matchedLearn = matchedTakeSkills.size();

					int mutual = Math.min(matchedTeach, matchedLearn);
					if (mutual == 0)
						continue;

					int totalCurrentSkills = currentUserHaveSkills.size() + currentUserNeedSkills.size();
					if (totalCurrentSkills == 0)
						continue;

					// Scoring: mutual*2 (since give+take), normalized by total skills
					int score = (int) (((double) mutual * 2 / totalCurrentSkills) * 100);
					score = (int) (Math.round(score / 10.0) * 10); // rounded to nearest 10


					// Prepare DTO to return matched user info
					MatchResultDto dto = new MatchResultDto();
					dto.setUserId(otherUser.getId());
					dto.setUserName(otherUser.getUsername());

					// Map skills to DTO - show only balanced pairs (take first 'balancedPairs' skills from sets)
					  dto.setCanLearn(mapSkillsById(matchedGiveSkills));
					   dto.setCanTeach(mapSkillsById(matchedTakeSkills));

					dto.setMatchingScore(score);
					dto.setProfileImageUrl(
							otherUser.getProfileImage() != null ? otherUser.getProfileImage().getImageUrl() : null);

					matchedUsers.add(dto);
				}
			}
		}

		// Sort matched users by descending match score
		matchedUsers.sort((a, b) -> b.getMatchingScore() - a.getMatchingScore());

		if (matchedUsers.isEmpty()) {
			return new ApiResponse<>(true, "No User Matched your Skills", Collections.emptyList());
		}
		return new ApiResponse<>(true, "Matched Successfully", matchedUsers);
	}

	// Helper to extract category IDs from User_Skill list
	private Set<Long> extractCategoryIds(List<User_Skill> skills) {
		return skills.stream().map(us -> us.getSkill().getCategory().getId()).collect(Collectors.toSet());
	}

	// Helper to get User by ID with exception if not found
	private User getUserById(Long currentUserId) {
		return userRepo.findById(currentUserId).orElseThrow(() -> new UserNotFoundException("User not Found"));
	}

	// Intersection of two sets
	private Set<Long> intersection(Set<Long> a, Set<Long> b) {
		Set<Long> result = new HashSet<>(a);
		result.retainAll(b);
		return result;
	}

	// Filter skill IDs by allowed category IDs
	private Set<Long> filterSkillIdsByCategories(List<User_Skill> skills, Set<Long> allowedCategoryIds) {
		return skills.stream().filter(us -> allowedCategoryIds.contains(us.getSkill().getCategory().getId()))
				.map(us -> us.getSkill().getId()).collect(Collectors.toSet());
	}

	// Map skill IDs to DTO objects
	private Set<MatchSkillDto> mapSkillsById(Set<Long> skillIds) {
		List<Skill> skills = skillRepo.findAllById(skillIds);
		return skills.stream().map(skill -> new MatchSkillDto(skill.getId(), skill.getSkillName()))
				.collect(Collectors.toSet());
	}
}
