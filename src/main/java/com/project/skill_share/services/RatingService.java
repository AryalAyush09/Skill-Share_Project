package com.project.skill_share.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.RatingResponseDto;
import com.project.skill_share.entities.Rating;
import com.project.skill_share.entities.Session;
import com.project.skill_share.entities.User;
import com.project.skill_share.repository.RatingRepository;
import com.project.skill_share.repository.SessionRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.ApiResponse;


@Service
public class RatingService {
	
  private final RatingRepository ratingRepo;
  private final SessionRepository sessionRepo;
  private final UserRepository userRepo;
  
  public RatingService(RatingRepository ratingRepo, SessionRepository sessionRepo, UserRepository userRepo) {
	  this.ratingRepo = ratingRepo;
	  this.sessionRepo = sessionRepo;
	  this.userRepo = userRepo;
  }
  
  public ApiResponse<?> submitRating(Long sessionId, Long raterId, 
		                          Long rateeId, int stars, String feedback){
	  Session ses = sessionRepo.findById(sessionId).orElse(null);
	  if(ses == null) {
		  return new ApiResponse<>(false, "Session not found", null);
	  }
	  Long user1Id = ses.getUser1Id();
	  Long user2Id = ses.getUser2Id();

	  if (!(
	      (user1Id.equals(raterId) && user2Id.equals(rateeId)) ||
	      (user2Id.equals(raterId) && user1Id.equals(rateeId))
	  )) {
	      return new ApiResponse<>(false, "Rater and ratee are not part of the given session", null);
	  }
	  
	  boolean exists = ratingRepo.existsBySessionIdAndRaterUserId(sessionId, raterId);
	  if(exists){
		  return new ApiResponse<>(false, "Rating already submitted!!", null);
	  }
	  Rating rating = new Rating();
	  rating.setFeedback(feedback);
	  rating.setRateeUserId(rateeId);
	  rating.setRaterUserId(raterId);
	  rating.setSession(ses);
	  rating.setStars(stars);
	  
	  ratingRepo.save(rating);
	  
	  User ratee = userRepo.findById(rateeId).orElse(null);
	  if(ratee != null) {
		  int total = ratee.getTotalRatings() == null ? 0 : ratee.getTotalRatings();
		  double currentAvg = ratee.getAverageRating() == null ? 0: ratee.getAverageRating();
		  double newAvg = ((currentAvg*total) + stars)/ (total+1);
		  newAvg = Math.round(newAvg * 10.0) / 10;
		  
		  ratee.setAverageRating(newAvg);
		  ratee.setTotalRatings(total + 1);
		  userRepo.save(ratee);
	  }
	  
	return new ApiResponse<>(true, "Rating Submitted", rating);
  } 
  
  public ApiResponse<?> getRatingsByUserId(Long userId, int page, int size) {
      Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
      Page<Rating> ratingPage = ratingRepo.findByRateeUserIdOrderByCreatedAtDesc(userId, pageable);

      List<RatingResponseDto> dtos = ratingPage.getContent().stream().map(rating -> {
    	    User rater = userRepo.findById(rating.getRaterUserId()).orElse(null);
    	    String raterName = rater != null ? rater.getUsername() : "Unknown";
    	    String imageUrl = rater != null && rater.getProfileImage() != null
    	            ? rater.getProfileImage().getImageUrl()
    	            : null;
    	    System.out.println("Fetching ratings for userId = " + userId);

    	    return new RatingResponseDto(
    	        raterName,
    	        imageUrl,
    	        rating.getStars(),
    	        rating.getFeedback(),
    	        rating.getCreatedAt()
    	    );
    	}).collect(Collectors.toList());

      return new ApiResponse<>(true, "Ratings fetched", dtos);
  }

}
