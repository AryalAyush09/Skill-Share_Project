//package com.project.skill_share.services;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//import com.project.skill_share.DTO.MatchResultDto;
//import com.project.skill_share.DTO.MatchSkillDto;
//import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
//import com.project.skill_share.entities.Skill;
//import com.project.skill_share.entities.User;
//import com.project.skill_share.entities.User_Skill;
//import com.project.skill_share.enums.SkillType;
//import com.project.skill_share.repository.SkillRepository;
//import com.project.skill_share.repository.UserRepository;
//import com.project.skill_share.repository.UserSkillRepository;
//import com.project.skill_share.response.ApiResponse;
//
//@Service
//public class MatchUserService {
//
//	private final UserRepository userRepo;
//	private final UserSkillRepository userSkillRepo;
//	private final SkillRepository skillRepo;
//
//	public MatchUserService(UserRepository userRepo, UserSkillRepository userSkillRepo, SkillRepository skillRepo) {
//		this.userRepo = userRepo;
//		this.userSkillRepo = userSkillRepo;
//		this.skillRepo = skillRepo;
//	}
//
//	  private static class MatchInfo {
//	        Set<MatchSkillDto> canTeach;
//	        Set<MatchSkillDto> canLearn;
//	        int matchingScore;
//
//	        MatchInfo(Set<MatchSkillDto> canTeach, Set<MatchSkillDto> canLearn, int matchingScore) {
//	            this.canTeach = canTeach;
//	            this.canLearn = canLearn;
//	            this.matchingScore = matchingScore;
//	        }
//	    }
//
//	    public ApiResponse<?> matchingUser(Long currentUserId) {
//	        User currentUser = getUserById(currentUserId);
//
//	        // Verify email status
//	        if (currentUser.getEmailStatus() != EmailTYPE.VERIFIED && currentUser.getEmailStatus() != EmailTYPE.ACTIVE) {
//	            throw new ResourceNotFoundException("User email is not verified or active");
//	        }
//
//	        // Get current user skills
//	        List<User_Skill> currentUserHaveSkills = userSkillRepo.findByUserAndType(currentUser, SkillType.HAVE);
//	        List<User_Skill> currentUserNeedSkills = userSkillRepo.findByUserAndType(currentUser, SkillType.NEED);
//
//	        List<User> otherUsers = userRepo.findAllExcept(currentUserId);
//
//	        List<MatchResultDto> matchedUsers = new ArrayList<>();
//
//	        for (User otherUser : otherUsers) {
//	            MatchInfo matchInfo = calculateMatchInfo(currentUser, otherUser, currentUserHaveSkills, currentUserNeedSkills);
//	            if (matchInfo == null) continue;
//
//	            MatchResultDto dto = new MatchResultDto();
//	            dto.setUserId(otherUser.getId());
//	            dto.setUserName(otherUser.getUsername());
//	            dto.setCanLearn(matchInfo.canLearn);
//	            dto.setCanTeach(matchInfo.canTeach);
//	            dto.setMatchingScore(matchInfo.matchingScore);
//	            dto.setProfileImageUrl(otherUser.getProfileImage() != null ? otherUser.getProfileImage().getImageUrl() : null);
//
//	            matchedUsers.add(dto);
//	        }
//
//	        matchedUsers.sort((a, b) -> b.getMatchingScore() - a.getMatchingScore());
//
//	        if (matchedUsers.isEmpty()) {
//	            return new ApiResponse<>(true, "No User Matched your Skills", Collections.emptyList());
//	        }
//	        return new ApiResponse<>(true, "Matched Successfully", matchedUsers);
//	    }
//
//	    public ApiResponse<?> getRequestDetail(Long matchId, Long viewerUserId) {
//	        MatchUser match = matchUserRepo.findById(matchId)
//	                .orElseThrow(() -> new UserNotFoundException("Match not found"));
//
//	        if (!match.getCurrentUserId().equals(viewerUserId) && !match.getOtherUserId().equals(viewerUserId)) {
//	            throw new UnauthorizedException("You are not authorized to view this match");
//	        }
//
//	        Long matchedUserId = match.getCurrentUserId().equals(viewerUserId) ? match.getOtherUserId() : match.getCurrentUserId();
//
//	        User viewerUser = getUserById(viewerUserId);
//	        User matchedUser = getUserById(matchedUserId);
//
//	        // Fetch current user skills once for efficiency
//	        List<User_Skill> viewerHaveSkills = userSkillRepo.findByUserAndType(viewerUser, SkillType.HAVE);
//	        List<User_Skill> viewerNeedSkills = userSkillRepo.findByUserAndType(viewerUser, SkillType.NEED);
//
//	        MatchInfo matchInfo = calculateMatchInfo(viewerUser, matchedUser, viewerHaveSkills, viewerNeedSkills);
//	        if (matchInfo == null) {
//	            return new ApiResponse<>(false, "No matching skills found between users", null);
//	        }
//
//	        MatchResultDto dto = new MatchResultDto();
//	        dto.setUserId(matchedUser.getId());
//	        dto.setUserName(matchedUser.getUsername());
//	        dto.setCanTeach(matchInfo.canTeach);
//	        dto.setCanLearn(matchInfo.canLearn);
//	        dto.setMatchingScore(match.getMatchingScore());
//	        dto.setProfileImageUrl(matchedUser.getProfileImage() != null ? matchedUser.getProfileImage().getImageUrl() : null);
//
//	        return new ApiResponse<>(true, "Match details fetched", dto);
//	    }
//
//	    // Helper method - full matching logic for two users, reuses skills lists if already fetched for currentUser
//	    private MatchInfo calculateMatchInfo(User currentUser, User otherUser, List<User_Skill> currentUserHaveSkills, List<User_Skill> currentUserNeedSkills) {
//	        List<User_Skill> otherHaveSkills = userSkillRepo.findByUserAndType(otherUser, SkillType.HAVE);
//	        List<User_Skill> otherNeedSkills = userSkillRepo.findByUserAndType(otherUser, SkillType.NEED);
//
//	        Set<Long> currentHaveCategoriesId = extractCategoryIds(currentUserHaveSkills);
//	        Set<Long> currentNeedCategoriesId = extractCategoryIds(currentUserNeedSkills);
//	        Set<Long> otherHaveCategoriesId = extractCategoryIds(otherHaveSkills);
//	        Set<Long> otherNeedCategoriesId = extractCategoryIds(otherNeedSkills);
//
//	        Set<Long> matchedGiveCategories = intersection(currentHaveCategoriesId, otherNeedCategoriesId);
//	        Set<Long> matchedTakeCategories = intersection(currentNeedCategoriesId, otherHaveCategoriesId);
//
//	        if (matchedGiveCategories.isEmpty() || matchedTakeCategories.isEmpty()) {
//	            return null; // no match
//	        }
//
//	        Set<Long> currentGiveSkills = filterSkillIdsByCategories(currentUserHaveSkills, matchedGiveCategories);
//	        Set<Long> currentTakeSkills = filterSkillIdsByCategories(currentUserNeedSkills, matchedTakeCategories);
//	        Set<Long> otherNeedSkillsInCategory = filterSkillIdsByCategories(otherNeedSkills, matchedGiveCategories);
//	        Set<Long> otherHaveSkillsInCategory = filterSkillIdsByCategories(otherHaveSkills, matchedTakeCategories);
//
//	        Set<Long> matchedGiveSkills = intersection(currentGiveSkills, otherNeedSkillsInCategory);
//	        Set<Long> matchedTakeSkills = intersection(currentTakeSkills, otherHaveSkillsInCategory);
//
//	        if (matchedGiveSkills.isEmpty() || matchedTakeSkills.isEmpty()) {
//	            return null; // no matched skills
//	        }
//
//	        int matchedTeach = matchedGiveSkills.size();
//	        int matchedLearn = matchedTakeSkills.size();
//
//	        int mutual = Math.min(matchedTeach, matchedLearn);
//	        if (mutual == 0) return null;
//
//	        int totalCurrentSkills = currentUserHaveSkills.size() + currentUserNeedSkills.size();
//	        int totalOtherSkills = otherHaveSkills.size() + otherNeedSkills.size();
//	        if (totalCurrentSkills == 0 || totalOtherSkills == 0) return null;
//
//	        double currentRatio = (double) mutual * 2 / totalCurrentSkills;
//	        double otherRatio = (double) mutual * 2 / totalOtherSkills;
//
//	        int balancedScore = (int) ((currentRatio + otherRatio) / 2 * 100);
//	        balancedScore = (int) (Math.round(balancedScore / 10.0) * 10);
//
//	        Set<MatchSkillDto> canLearn = mapSkillsById(matchedGiveSkills);
//	        Set<MatchSkillDto> canTeach = mapSkillsById(matchedTakeSkills);
//
//	        return new MatchInfo(canTeach, canLearn, balancedScore);
//	    }
//
//	    private Set<Long> extractCategoryIds(List<User_Skill> skills) {
//	        return skills.stream()
//	                .map(us -> us.getSkill().getCategory().getId())
//	                .collect(Collectors.toSet());
//	    }
//
//	    private User getUserById(Long id) {
//	        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//	    }
//
//	    private Set<Long> intersection(Set<Long> a, Set<Long> b) {
//	        Set<Long> result = new HashSet<>(a);
//	        result.retainAll(b);
//	        return result;
//	    }
//
//	    private Set<Long> filterSkillIdsByCategories(List<User_Skill> skills, Set<Long> allowedCategories) {
//	        return skills.stream()
//	                .filter(us -> allowedCategories.contains(us.getSkill().getCategory().getId()))
//	                .map(us -> us.getSkill().getId())
//	                .collect(Collectors.toSet());
//	    }
//
//	    private Set<MatchSkillDto> mapSkillsById(Set<Long> skillIds) {
//	        List<Skill> skills = skillRepo.findAllById(skillIds);
//	        return skills.stream()
//	                .map(skill -> new MatchSkillDto(skill.getId(), skill.getSkillName()))
//	                .collect(Collectors.toSet());
//	    }
//}
