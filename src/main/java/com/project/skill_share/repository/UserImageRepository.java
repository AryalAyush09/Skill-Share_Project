package com.project.skill_share.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.skill_share.entities.User;
import com.project.skill_share.entities.UserImage;
import com.project.skill_share.enums.ImageType;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
	
	//get all image
     List<UserImage>findByUser(User user);
     
     // profile or certificate photo acc to type
     List<UserImage> findByUserAndImageType(User user, ImageType imageType);

     UserImage findTopByUserOrderByCreatedAtDesc(User user);
     
     //delete the image from cloudinary 
     Optional<UserImage>findByPublicId(User user);
     
     // if user deletes their account then image also be deleted 
     void deleteAllByUser(User user);
}
