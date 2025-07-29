package com.project.skill_share.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.MatchUser;
import com.project.skill_share.enums.MatchStatus;

@Repository
public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {

    // Check if mutual confirmed or pending match already exists between two users using enum parameters
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
           "FROM MatchUser m WHERE ((m.currentUserId = :u1 AND m.otherUserId = :u2) " +
           "OR (m.currentUserId = :u2 AND m.otherUserId = :u1)) " +
           "AND (m.matchStatus = :confirmed OR m.matchStatus = :pending)")
    boolean existsConfirmedorPendingMatchBetweenUsers(
        @Param("u1") Long u1,
        @Param("u2") Long u2,
        @Param("confirmed") MatchStatus confirmed,
        @Param("pending") MatchStatus pending);

    // Find all confirmed matches for a user (either as currentUser or otherUser)
    @Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) " +
           "AND m.matchStatus = :status")
    List<MatchUser> findByUserIdAndStatus(
        @Param("userId") Long userId,
        @Param("status") MatchStatus status);

    // Convenience method for confirmed matches
    default List<MatchUser> findConfirmedByUserId(Long userId) {
        return findByUserIdAndStatus(userId, MatchStatus.CONFIRMED);
    }

    // Convenience method for pending requests sent to a user
    List<MatchUser> findByOtherUserIdAndMatchStatus(Long otherUserId, MatchStatus status);

    // Find a specific match request between two users with a given status
    Optional<MatchUser> findByCurrentUserIdAndOtherUserIdAndMatchStatus(
        Long currentUserId, Long otherUserId, MatchStatus status);

	Optional<MatchUser> findByCurrentUserIdAndOtherUserId(Long currentUserId, Long requesterUserId);

//	boolean existsByUsersAndStatus(Long userA, Long userB, MatchStatus confirmed);

	boolean existsByCurrentUserIdAndOtherUserIdAndMatchStatus(Long currentUserId, Long otherUserId, MatchStatus confirmed);

	@Query("SELECT m FROM MatchUser m WHERE (m.currentUserId = :userId OR m.otherUserId = :userId) AND m.matchStatus = :status")
	List<MatchUser> findConfirmedMatches(@Param("userId") Long userId, @Param("status") MatchStatus status);

}
