package com.project.skill_share.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.Set;

import com.project.skill_share.DTO.MatchRequestDto;
import com.project.skill_share.DTO.MatchResultDto;
import com.project.skill_share.DTO.MatchSkillDto;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.GlobalErrorHandler.UnauthorizedException;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.MatchUser;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.User_Skill;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.MatchStatus;
import com.project.skill_share.enums.SkillType;
import com.project.skill_share.repository.MatchUserRepository;
import com.project.skill_share.repository.NotificationRepository;
import com.project.skill_share.repository.SkillRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.repository.UserSkillRepository;
import com.project.skill_share.response.ApiResponse;

@Service
public class MatchService {

	private final UserRepository userRepo;
	private final UserSkillRepository userSkillRepo;
	private final SkillRepository skillRepo;
	private final MatchUserRepository matchUserRepo;
	private final NotificationService notificationService;
	
	public MatchService(UserRepository userRepo, UserSkillRepository userSkillRepo,
			MatchUserRepository matchUserRepo, SkillRepository skillRepo, 
			NotificationService notificationService,NotificationRepository notificationRepo) {
		this.userRepo = userRepo;
		this.userSkillRepo = userSkillRepo;
		this.skillRepo = skillRepo;
		this.matchUserRepo = matchUserRepo;
		this.notificationService = notificationService;
	}
	
	private static class MatchInfo{
		Set<MatchSkillDto> canTeach;
		Set<MatchSkillDto> canLearn;
		int matchingScore;
		
		 MatchInfo(Set<MatchSkillDto> canTeach, Set<MatchSkillDto> canLearn, int matchingScore) {
	            this.canTeach = canTeach;
	            this.canLearn = canLearn;
	            this.matchingScore = matchingScore;
	        }
	}

   public ApiResponse<?> matchingUser(Long currentUserId){
		
		User currentUser = getUserById(currentUserId);
		
		 if (currentUser.getEmailStatus() != EmailTYPE.VERIFIED && currentUser.getEmailStatus() != EmailTYPE.ACTIVE) {
	            throw new ResourceNotFoundException("User email is not verified or active");
	        }
		 
		//get Current User Skills
		List<User_Skill> currentUserHaveSkills =
                userSkillRepo.findByUserAndType(currentUser,SkillType.HAVE);
 
		List<User_Skill> currentUserNeedSkills = 
				userSkillRepo.findByUserAndType(currentUser, SkillType.NEED);
	     
		//extract categories ID
//		Set<Long> currentHaveCategoriesId = extractCategoryIds(currentUserHaveSkills);
//		Set<Long> currentNeedCategoriesId = extractCategoryIds(currentUserNeedSkills);
	
		List<User> allOtherUser = userRepo.findAllExcept(currentUserId);
		
		List<MatchResultDto> matchedUsers = new ArrayList<>();

	for(User otherUser : allOtherUser) {
			
		//get other User Skills	
//		List<User_Skill> otherHaveSkills = 
//				userSkillRepo.findByUserAndType(otherUser, SkillType.HAVE);
//		List<User_Skill> otherNeedSkills = 
//				userSkillRepo.findByUserAndType(otherUser,SkillType.NEED);
//			
//		//extract skills categoryId
//	    Set<Long> otherHaveCategoriesId = extractCategoryIds(otherHaveSkills);
//		Set<Long> otherNeedCategoriesId = extractCategoryIds(otherNeedSkills);
//		
//		System.out.println("Current HAVE Category IDs: " + currentHaveCategoriesId);
//		System.out.println("Current NEED Category IDs: " + currentNeedCategoriesId);
//		System.out.println("Other " + otherUser.getId() + " HAVE Categories: " + otherHaveCategoriesId);
//		System.out.println("Other " + otherUser.getId() + " NEED Categories: " + otherNeedCategoriesId);
//  
//		//check for matching the Categories
//		Set<Long> matchedGiveCategories = intersection(currentHaveCategoriesId, otherNeedCategoriesId);
//	    Set<Long> matchedTakeCategories = intersection(currentNeedCategoriesId, otherHaveCategoriesId);
//	   
//	    System.out.println("Checking with user: " + otherUser.getId());
//	    System.out.println("giveMatch: " + matchedGiveCategories);
//	    System.out.println("takeMatch: " + matchedTakeCategories);
//	    
//	    //check whether Categories is Matched or not 
//	    if (matchedGiveCategories.isEmpty() || matchedTakeCategories.isEmpty()) {
//	    	continue;
//	    }	
//	    	//Filter or match the skills with each Category matched
//	    	Set<Long> currentGiveSkills = 
//	    			filterSkillIdsByCategories(currentUserHaveSkills,matchedGiveCategories);
//	    	Set<Long> currentTakeSkills = 
//	    			filterSkillIdsByCategories(currentUserNeedSkills, matchedTakeCategories);
//	    	
//	    	Set<Long> otherNeedSkillsInCategory = 
//	    		    filterSkillIdsByCategories(otherNeedSkills, matchedGiveCategories);
//	    	Set<Long> otherHaveSkillsInCategory = 
//	    		    filterSkillIdsByCategories(otherHaveSkills, matchedTakeCategories);
//	    	
//			Set<Long> matchedGiveSkills = intersection(currentGiveSkills,otherNeedSkillsInCategory);
//            Set<Long> matchedTakeSkills = intersection(currentTakeSkills, otherHaveSkillsInCategory);
//            System.out.println("matchedGiveSkills: " + matchedGiveSkills);
//            System.out.println("matchedTakeSkills: " + matchedTakeSkills);
//            System.out.println("currentGiveSkills: " + currentGiveSkills);
//            System.out.println("otherNeedSkillsInCategory: " + otherNeedSkillsInCategory);
//            System.out.println("currentTakeSkills: " + currentTakeSkills);
//            System.out.println("otherHaveSkillsInCategory: " + otherHaveSkillsInCategory);
//	
//            if (!matchedGiveSkills.isEmpty() && !matchedTakeSkills.isEmpty()) {
//            	int matchedTeach = matchedGiveSkills.size();
//				int matchedLearn = matchedTakeSkills.size();
//
//				int mutual = Math.min(matchedTeach, matchedLearn);
//				if (mutual == 0)
//					continue;
//
//				int totalCurrentSkills = currentUserHaveSkills.size() + currentUserNeedSkills.size();
//				int totalOtherSkills = otherHaveSkills.size() + otherNeedSkills.size();
//				
//				if (totalCurrentSkills == 0 || totalOtherSkills == 0)
//					continue;
//				
//				double currentRatio = (double) mutual * 2 / totalCurrentSkills;
//				double otherRatio = (double) mutual * 2 / totalOtherSkills;
//				
//				int balancedScore =  (int) ((currentRatio + otherRatio) /2 *100);
//				balancedScore = (int) (Math.round(balancedScore / 10.0) *10);
		
		   MatchInfo matchInfo = calculateMatchInfo
				(currentUser, otherUser, currentUserHaveSkills, currentUserNeedSkills);
		   if(matchInfo == null) continue;
        
			  MatchResultDto dto = new MatchResultDto();
			   dto.setUserId(otherUser.getId());
			   dto.setUserName(otherUser.getUsername());
			   dto.setCanLearn(matchInfo.canLearn);
			   dto.setCanTeach(matchInfo.canTeach);
			   dto.setMatchingScore(matchInfo.matchingScore);
			   dto.setProfileImageUrl(
					    otherUser.getProfileImage() != null ? otherUser.getProfileImage().getImageUrl() : null);
			   matchedUsers.add(dto);
		    }

	      matchedUsers.sort((a, b) -> b.getMatchingScore() - a.getMatchingScore());

	   
	   if(matchedUsers.isEmpty()) {
		   return new ApiResponse<>(true, "No User Matched your SKills", Collections.emptyList());
	   }
		return new ApiResponse<>(true, "Matched Successfully", matchedUsers);
	}
   
   public ApiResponse<?> getRequestDetail(Long matchId, Long viewerUserId){
	   MatchUser matchUser = matchUserRepo.findById(matchId)
			   .orElseThrow(() -> new UserNotFoundException("Match not Found"));
	   
	   if(!matchUser.getCurrentUserId().equals(viewerUserId) && 
			   !matchUser.getOtherUserId().equals(matchId)) {
		   throw new UnauthorizedException("You are not authorized to view the match");
	   }
	  Long matchUserId =  matchUser.getCurrentUserId().equals(viewerUserId) ?
			  matchUser.getOtherUserId():
				  matchUser.getCurrentUserId();
	  
	  User viewerUser = getUserById(viewerUserId);
	  User matchedUser = getUserById(matchId);
	  
	  List<User_Skill> viewerHaveSkills = userSkillRepo.findByUserAndType(viewerUser, SkillType.HAVE);
	  List<User_Skill> viewerNeedSkills = userSkillRepo.findByUserAndType(viewerUser, SkillType.NEED);
	  
	  MatchInfo matchInfo = calculateMatchInfo(viewerUser, matchedUser, viewerHaveSkills, viewerNeedSkills);
	  
	    if (matchInfo == null) {
            return new ApiResponse<>(false, "No matching skills found between users", null);
        }
	    
	    MatchResultDto dto = new MatchResultDto();
        dto.setUserId(matchedUser.getId());
        dto.setUserName(matchedUser.getUsername());
        dto.setCanTeach(matchInfo.canTeach);
        dto.setCanLearn(matchInfo.canLearn);
        dto.setMatchingScore((int) matchUser.getMatchingScore());
        dto.setProfileImageUrl(matchedUser.getProfileImage() != null ? matchedUser.getProfileImage().getImageUrl() : null);

        return new ApiResponse<>(true, "Match details fetched", dto);
    }
   
   private MatchInfo calculateMatchInfo(User currentUser, User otherUser, List<User_Skill> currentUserHaveSkills, List<User_Skill> currentUserNeedSkills) {
       List<User_Skill> otherHaveSkills = userSkillRepo.findByUserAndType
    		   (otherUser, SkillType.HAVE);
       List<User_Skill> otherNeedSkills = userSkillRepo.findByUserAndType
    		   (otherUser, SkillType.NEED);

       Set<Long> currentHaveCategoriesId = extractCategoryIds(currentUserHaveSkills);
       Set<Long> currentNeedCategoriesId = extractCategoryIds(currentUserNeedSkills);
       Set<Long> otherHaveCategoriesId = extractCategoryIds(otherHaveSkills);
       Set<Long> otherNeedCategoriesId = extractCategoryIds(otherNeedSkills);

       Set<Long> matchedGiveCategories = intersection(currentHaveCategoriesId, otherNeedCategoriesId);
       Set<Long> matchedTakeCategories = intersection(currentNeedCategoriesId, otherHaveCategoriesId);

       if (matchedGiveCategories.isEmpty() || matchedTakeCategories.isEmpty()) {
           return null; // no match
       }

       Set<Long> currentGiveSkills = filterSkillIdsByCategories
    		   (currentUserHaveSkills, matchedGiveCategories);
       Set<Long> currentTakeSkills = filterSkillIdsByCategories
    		   (currentUserNeedSkills, matchedTakeCategories);
       
       Set<Long> otherNeedSkillsInCategory = filterSkillIdsByCategories
    		   (otherNeedSkills, matchedGiveCategories);
       Set<Long> otherHaveSkillsInCategory = filterSkillIdsByCategories
    		   (otherHaveSkills, matchedTakeCategories);

       Set<Long> matchedGiveSkills = intersection(currentGiveSkills, otherNeedSkillsInCategory);
       Set<Long> matchedTakeSkills = intersection(currentTakeSkills, otherHaveSkillsInCategory);

       if (matchedGiveSkills.isEmpty() || matchedTakeSkills.isEmpty()) {
           return null; // no matched skills
       }

       int matchedTeach = matchedGiveSkills.size();
       int matchedLearn = matchedTakeSkills.size();

       int mutual = Math.min(matchedTeach, matchedLearn);
       if (mutual == 0) return null;

       int totalCurrentSkills = currentUserHaveSkills.size() + currentUserNeedSkills.size();
       int totalOtherSkills = otherHaveSkills.size() + otherNeedSkills.size();
       if (totalCurrentSkills == 0 || totalOtherSkills == 0) return null;

       double currentRatio = (double) mutual * 2 / totalCurrentSkills;
       double otherRatio = (double) mutual * 2 / totalOtherSkills;

       int balancedScore = (int) ((currentRatio + otherRatio) / 2 * 100);
       balancedScore = (int) (Math.round(balancedScore / 10.0) * 10);

       Set<MatchSkillDto> canLearn = mapSkillsById(matchedGiveSkills);
       Set<MatchSkillDto> canTeach = mapSkillsById(matchedTakeSkills);

       return new MatchInfo(canTeach, canLearn, balancedScore);
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
	
	
	public ApiResponse<?> sendRequest(Long currentUserId, MatchRequestDto dto){
         User currentUser = getUserById(currentUserId);
        
		Long targetUserId = dto.getTargetUserId();
		if (targetUserId == null) {
		    return new ApiResponse<>(false, "Target userId must not be null", null);
		}

		float matchingScore = dto.getMatchingScore();
		
		if(currentUserId.equals(targetUserId)) {
			return new ApiResponse<>(false, "you cant send request to yourself", null);	
		}
		 boolean exists = matchUserRepo.existsConfirmedorPendingMatchBetweenUsers(currentUserId, targetUserId);
		 if(exists) {
			 return new ApiResponse<>(false, "Match request already exists", null);
		 }
		 System.out.println("Request sent by userId: " + currentUserId);
		 System.out.println("Target userId: " + dto.getTargetUserId());

		 getUserById(currentUserId);
	     getUserById(targetUserId);
		
		MatchUser matchUser = new MatchUser();
		matchUser.setCurrentUserId(currentUserId);
		matchUser.setOtherUserId(targetUserId);
		matchUser.setMatchingScore(matchingScore);
		matchUser.setMatchStatus(MatchStatus.PENDING);
		
		matchUserRepo.save(matchUser);
		
		String notifMessage = currentUser.getUsername() + " sent you a skill match request.";
		notificationService.sendNotification(targetUserId, notifMessage, currentUserId);
	    
		String senderMessage = "You have sent matching Request to " +  getUserById(targetUserId).getUsername();
		notificationService.sendNotification(currentUserId, senderMessage, targetUserId);
		
		return new ApiResponse<>(true, "Match Request Sent Successfully", null);
	}
		

	public ApiResponse<?> respondToRequest(Long currentUserId, Long requesterUserId, MatchStatus responseStatus) {
	  
	    if (responseStatus != MatchStatus.CONFIRMED && responseStatus != MatchStatus.REJECTED) {
	        return new ApiResponse<>(false, "Invalid response status", null);
	    }

	    MatchUser matchRequest = matchUserRepo
	        .findByCurrentUserIdAndOtherUserIdAndMatchStatus(requesterUserId, currentUserId, MatchStatus.PENDING)
	        .orElse(null);

	    if (matchRequest == null) {
	        return new ApiResponse<>(false, "No pending match request found", null);
	    }
	    
	    matchRequest.setMatchStatus(responseStatus);
	    matchUserRepo.save(matchRequest);
	    
	    String message = responseStatus == MatchStatus.CONFIRMED ?
	        "Your skill match request was accepted." :
	        "Your skill match request was rejected.";

	    notificationService.sendNotification(requesterUserId, message, currentUserId);

	     if(responseStatus == MatchStatus.CONFIRMED) {
	        MatchUser reciprocal = new MatchUser();
	        reciprocal.setCurrentUserId(currentUserId);
	        reciprocal.setOtherUserId(requesterUserId);
	        reciprocal.setMatchingScore(matchRequest.getMatchingScore());
	        reciprocal.setMatchStatus(MatchStatus.CONFIRMED);
	        matchUserRepo.save(reciprocal);
	    }
	    return new ApiResponse<>(true, "Request " + responseStatus.name().toLowerCase() + " successfully", null);
	}

}