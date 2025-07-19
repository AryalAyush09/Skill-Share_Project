package com.project.skill_share.DTO;

import java.util.Set;

public class MatchResultDto {
	
  private Long userId;
  private String userName;
  private int matchingScore;
  private String profileImageUrl;
  private Set<MatchSkillDto> canTeach;
  private Set<MatchSkillDto> canLearn;
  
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}

public int getMatchingScore() {
	return matchingScore;
}
public void setMatchingScore(int matchingScore) {
	this.matchingScore = matchingScore;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getProfileImageUrl() {
	return profileImageUrl;
}
public void setProfileImageUrl(String profileImageUrl) {
	this.profileImageUrl = profileImageUrl;
}
public Set<MatchSkillDto> getCanTeach() {
	return canTeach;
}
public void setCanTeach(Set<MatchSkillDto> canTeach) {
	this.canTeach = canTeach;
}
public Set<MatchSkillDto> getCanLearn() {
	return canLearn;
}
public void setCanLearn(Set<MatchSkillDto> canLearn) {
	this.canLearn = canLearn;
}

}
