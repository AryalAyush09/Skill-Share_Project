package com.project.skill_share.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.skill_share.enums.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_box")

public class MessageBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false, insertable = false, updatable = false)
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

  
    @Column(name = "receiver_id", nullable = false, insertable = false, updatable = false)
    private Long receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_seen")
    private boolean isSeen = false;

    @Enumerated(EnumType.STRING)
    private MessageType msgType;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getSenderId() {
	return senderId;
}

public void setSenderId(Long senderId) {
	this.senderId = senderId;
}

public User getSender() {
	return sender;
}

public void setSender(User sender) {
	this.sender = sender;
}

public Long getReceiverId() {
	return receiverId;
}

public void setReceiverId(Long receiverId) {
	this.receiverId = receiverId;
}

public User getReceiver() {
	return receiver;
}

public void setReceiver(User receiver) {
	this.receiver = receiver;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public MessageType getMsgType() {
	return msgType;
}

public void setMsgType(MessageType msgType) {
	this.msgType = msgType;
}

public boolean isSeen() {
	return isSeen;
}

public void setSeen(boolean isSeen) {
	this.isSeen = isSeen;
}

public LocalDateTime getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}
}
