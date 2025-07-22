package com.project.skill_share.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.skill_share.entities.MessageBox;

public interface MessageBoxRepository extends JpaRepository<MessageBox, Long> {
       Page<MessageBox> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(Long senderId1, Long receiverId1,
    		   Long SenderId2, Long receiverId2, Pageable pageable);
   
       long countByReceiverIdAndIsSeenFalse(Long receiverId);

       @Modifying
       @Query("UPDATE MessageBox m SET m.isSeen = true WHERE m.senderId = :senderId AND m.receiverId = :receiverId AND m.isSeen = false")
       void markMessagesAsSeen(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
