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
public class MatchService {

	private final UserRepository userRepo;
	private final UserSkillRepository userSkillRepo;
	private final SkillRepository skillRepo;

	public MatchService(UserRepository userRepo, UserSkillRepository userSkillRepo, SkillRepository skillRepo) {
		this.userRepo = userRepo;
		this.userSkillRepo = userSkillRepo;
		this.skillRepo = skillRepo;
	}

  public ApiResponse<?> matchingUser(Long currentUserId){
		
		User currentUser = getUserById(currentUserId);
        
		//get Current User Skills
		List<User_Skill> currentUserHaveSkills =
                userSkillRepo.findByUserAndType(currentUser,SkillType.HAVE);
 
		List<User_Skill> currentUserNeedSkills = 
				userSkillRepo.findByUserAndType(currentUser, SkillType.NEED);
	     
		//extract categories ID
		Set<Long> currentHaveCategoriesId = extractCategoryIds(currentUserHaveSkills);
		Set<Long> currentNeedCategoriesId = extractCategoryIds(currentUserNeedSkills);
		
		List<User> allOtherUser = userRepo.findAllExcept(currentUserId);
		
		List<MatchResultDto> matchedUsers = new ArrayList<>();

	for(User otherUser : allOtherUser) {
		
		//get other User Skills	
		List<User_Skill> otherHaveSkills = 
				userSkillRepo.findByUserAndType(otherUser, SkillType.HAVE);
		List<User_Skill> otherNeedSkills = 
				userSkillRepo.findByUserAndType(otherUser,SkillType.NEED);
			
		//extract skills categoryId
	    Set<Long> otherHaveCategoriesId = extractCategoryIds(otherHaveSkills);
		Set<Long> otherNeedCategoriesId = extractCategoryIds(otherNeedSkills);
		
		System.out.println("Current HAVE Category IDs: " + currentHaveCategoriesId);
		System.out.println("Current NEED Category IDs: " + currentNeedCategoriesId);
		System.out.println("Other " + otherUser.getId() + " HAVE Categories: " + otherHaveCategoriesId);
		System.out.println("Other " + otherUser.getId() + " NEED Categories: " + otherNeedCategoriesId);
  
		//check for matching the Categories
		Set<Long> matchedGiveCategories = intersection(currentHaveCategoriesId, otherNeedCategoriesId);
	    Set<Long> matchedTakeCategories = intersection(currentNeedCategoriesId, otherHaveCategoriesId);
	   
	    System.out.println("Checking with user: " + otherUser.getId());
	    System.out.println("giveMatch: " + matchedGiveCategories);
	    System.out.println("takeMatch: " + matchedTakeCategories);
	    
	    //check whether Categories is Matched or not 
	    if(!matchedGiveCategories.isEmpty() && !matchedTakeCategories.isEmpty()) {
	    	
	    	//Filter or match the skills with each Category matched
	    	Set<Long> currentGiveSkills = 
	    			filterSkillIdsByCategories(currentUserHaveSkills,matchedGiveCategories);
	    	Set<Long> currentTakeSkills = 
	    			filterSkillIdsByCategories(currentUserNeedSkills, matchedTakeCategories);
	    	
	    	Set<Long> otherNeedSkillsInCategory = 
	    		    filterSkillIdsByCategories(otherNeedSkills, matchedGiveCategories);
	    	Set<Long> otherHaveSkillsInCategory = 
	    		    filterSkillIdsByCategories(otherHaveSkills, matchedTakeCategories);
	    	
			Set<Long> matchedGiveSkills = intersection(currentGiveSkills,otherNeedSkillsInCategory);
            Set<Long> matchedTakeSkills = intersection(currentTakeSkills, otherHaveSkillsInCategory);
            System.out.println("matchedGiveSkills: " + matchedGiveSkills);
            System.out.println("matchedTakeSkills: " + matchedTakeSkills);
            System.out.println("currentGiveSkills: " + currentGiveSkills);
            System.out.println("otherNeedSkillsInCategory: " + otherNeedSkillsInCategory);
            System.out.println("currentTakeSkills: " + currentTakeSkills);
            System.out.println("otherHaveSkillsInCategory: " + otherHaveSkillsInCategory);
	
		    if(!matchedGiveSkills.isEmpty() && !matchedTakeSkills.isEmpty()) {
		      int countGive = matchedGiveSkills.size();
			  int countTake = matchedTakeSkills.size();
			  int totalSkills = otherNeedSkillsInCategory.size() + otherHaveSkillsInCategory.size();
			  int percentage  = (countGive + countTake)/(totalSkills)*100;
				
			  MatchResultDto dto = new MatchResultDto();
			   dto.setUserId(otherUser.getId());
			   dto.setUserName(otherUser.getUsername());
			   dto.setCanLearn(mapSkillsById(matchedGiveSkills));
			   dto.setCanTeach(mapSkillsById(matchedTakeSkills));
			   dto.setPercentage(percentage);
			   dto.setProfileImageUrl(
					    otherUser.getProfileImage() != null ? otherUser.getProfileImage().getImageUrl() : null);
			   matchedUsers.add(dto);
		    }
		  }
		}
	   matchedUsers.sort((a, b) -> b.getPercentage() - a.getPercentage());
	   
	   if(matchedUsers.isEmpty()) {
		   return new ApiResponse<>(true, "No User Matched your SKills", Collections.emptyList());
	   }
		return new ApiResponse<>(true, "Matched Successfully", matchedUsers);
	}
	
	private Set<Long> extractCategoryIds(List<User_Skill> skills){
		return skills.stream()
				.map(us -> us.getSkill().getCategory().getId()).collect(Collectors.toSet());
	}

	private User getUserById(Long currentUserId) {
		return userRepo.findById(currentUserId).orElseThrow(() -> new UserNotFoundException("User not Found"));
	}
	
	private Set<Long> intersection(Set<Long> a, Set<Long> b){
		Set<Long> result = new HashSet<>(a);
		result.retainAll(b);
		return result;
	}
	
	private Set<Long> filterSkillIdsByCategories(List<User_Skill> Skills, Set<Long>allowedCategoryIds){
		return Skills.stream()
				.filter(us -> allowedCategoryIds.contains(us.getSkill().getCategory().getId())) 
		        .map(us -> us.getSkill().getId()).collect(Collectors.toSet());
	}
	
	private Set<MatchSkillDto> mapSkillsById(Set<Long> skillIds){
		List<Skill> skills = skillRepo.findAllById(skillIds);
		return skills.stream().map(skill -> new MatchSkillDto(skill.getId(), skill.getSkillName()))
				.collect(Collectors.toSet());
	}
}
