package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.RatingRequestDto;
import com.project.skill_share.services.RatingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class RatingController {
      private final RatingService rateService;
      
      public RatingController(RatingService rateService) {
    	  this.rateService = rateService;
      }
      
      @Operation(summary = "Rate the matched User")
      @PostMapping("/send/rating")
      public ResponseEntity<?> ratingUser(@RequestBody RatingRequestDto dto, Authentication auth) {
          Long raterId = Long.parseLong(auth.getName()); // from JWT
          return ResponseEntity.ok(rateService.submitRating(
              dto.getSessionId(),
              raterId,
              dto.getRateeId(),
              dto.getStars(),
              dto.getFeedback()));
      }
      
      @Operation(summary = "Get rating from other user")
      @GetMapping("/me")
      public ResponseEntity<?> getMyRatings(Authentication auth,@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
          Long userId = Long.parseLong(auth.getName()); 
          return ResponseEntity.ok(rateService.getRatingsByUserId(userId, page, size));
      }
}
