//package com.project.skill_share.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.project.skill_share.entities.MatchUser;
//import com.project.skill_share.enums.MatchStatus;
//
//@Repository
//public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {
//
//    // Check if mutual confirmed match already exists
//    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
//           "FROM MatchUser m WHERE ((m.currentUserId = :u1 AND m.otherUserId = :u2) " +
//           "OR (m.currentUserId = :u2 AND m.otherUserId = :u1)) AND m.matchStatus = 'CONFIRMED'OR m.matchStatus = 'PENDING")
//    boolean existsConfirmedorPendingMatchBetweenUsers(Long u1, Long u2);
//
//    //  Find confirmed matches for a user
//    @Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) AND m.matchStatus = 'CONFIRMED'")
//    Optional<MatchUser> findConfirmedByUserId(Long userId);
//    
//    @Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) AND m.matchStatus = 'REJECTED'")
//    List<MatchUser> findByUserIdAndStatus(Long userId, MatchStatus status);  
//}
package com.project.skill_share.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.MatchUser;
import com.project.skill_share.enums.MatchStatus;

@Repository
public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {

    // Check if mutual confirmed or pending match already exists between two users
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
           "FROM MatchUser m WHERE ((m.currentUserId = :u1 AND m.otherUserId = :u2) " +
           "OR (m.currentUserId = :u2 AND m.otherUserId = :u1)) " +
           "AND (m.matchStatus = 'CONFIRMED' OR m.matchStatus = 'PENDING')")
    boolean existsConfirmedorPendingMatchBetweenUsers(Long u1, Long u2);

    // Find confirmed matches for a user (either as currentUser or otherUser)
    @Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) " +
           "AND m.matchStatus = 'CONFIRMED'")
    Optional<MatchUser> findConfirmedByUserId(Long userId);
    
    // Find matches for a user with a specific status (REJECTED, etc.)
    @Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) " +
           "AND m.matchStatus = :status")
    List<MatchUser> findByUserIdAndStatus(Long userId, MatchStatus status);

    Optional<MatchUser> findByCurrentUserIdAndOtherUserIdAndMatchStatus(Long currentUserId, Long otherUserId, MatchStatus status);

}
