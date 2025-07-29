package com.project.skill_share.services;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.project.skill_share.entities.MatchUser;
import com.project.skill_share.entities.Session;
import com.project.skill_share.enums.MatchStatus;
import com.project.skill_share.repository.MatchUserRepository;
import com.project.skill_share.repository.SessionRepository;
import com.project.skill_share.response.ApiResponse;

@Service
public class SessionService {
	private final SessionRepository sessionRepo;
	private final MatchUserRepository matchUserRepo;
	
	public SessionService(SessionRepository sessionRepo, MatchUserRepository matchUserRepo) {
		this.sessionRepo = sessionRepo;
		this.matchUserRepo = matchUserRepo;
	}
	
	public ApiResponse<?> startSession(Long user1Id, Long user2Id, Long matchId) {
	    MatchUser match = matchUserRepo.findById(matchId).orElse(null);
	    if (match == null) {
	        return new ApiResponse<>(false, "No Match found for User", null);
	    }

	    // Validate if user1Id and user2Id match the MatchUser entity
	    boolean isValidUsers = 
	        (match.getCurrentUserId().equals(user1Id) && match.getOtherUserId().equals(user2Id)) ||
	        (match.getCurrentUserId().equals(user2Id) && match.getOtherUserId().equals(user1Id));

	    if (!isValidUsers) {
	        return new ApiResponse<>(false, "Provided users do not match the users in the Match record", null);
	    }

	    if (sessionRepo.existsByMatch(match)) {
	        return new ApiResponse<>(false, "Session already exists for this match", null);
	    }
	    
	    Session session = new Session();
	    session.setUser1Id(user1Id);
	    session.setUser2Id(user2Id);
	    session.setMatch(match);
	    session.setStartedAt(LocalDateTime.now());
	    session.setType(MatchStatus.IN_PROGRESS);

	    sessionRepo.save(session);

	    return new ApiResponse<>(true, "Session started", null);
	}

  
  public ApiResponse<?> endSession(Long SessionId){
	  Session session = sessionRepo.findById(SessionId).orElse(null);
	  if(session == null) {
		  return new ApiResponse<>(false, "No any session started" , null);
	  }
	  if(session.getType() == MatchStatus.COMPLETED) {
		    return new ApiResponse<>(false, "Session already ended", session);
	  }
	  
	  LocalDateTime end = LocalDateTime.now();
	  session.setEndedAt(end);
	  session.setDurationInMinutes(Duration.between(session.getStartedAt(),end).toMinutes());
	  session.setType(MatchStatus.COMPLETED);
	  
	  sessionRepo.save(session);
	  return new ApiResponse<>(true, "Session Completed", session);
  }
}
