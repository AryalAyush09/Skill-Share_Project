package com.project.skill_share.DTO;

import java.time.LocalDateTime;

import com.project.skill_share.enums.ImageType;

public class ImageDto {
   private String imageUrl;
   private String publicId;
   private ImageType imageType;
   private LocalDateTime createdAt;
   
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getPublicId() {
	return publicId;
}
public void setPublicId(String publicId) {
	this.publicId = publicId;
}
public ImageType getImageType() {
	return imageType;
}
public void setImageType(ImageType imageType) {
	this.imageType = imageType;
}
public LocalDateTime getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}
}
