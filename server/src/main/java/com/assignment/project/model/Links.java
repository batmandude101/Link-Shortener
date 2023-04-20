package com.assignment.project.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "link_lookup")
public class Links {
	
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private Long pkLinkID; 
	
	private String actualLink; 
	
	private String shortenedLink;
	
	private Date createTime = new Date();

	private boolean expired = false;
	public Long getPkLinkID() {
		return pkLinkID;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public void setPkLinkID(Long pkLinkID) {
		this.pkLinkID = pkLinkID;
	}

	public String getActualLink() {
		return actualLink;
	}

	public void setActualLink(String actualLink) {
		this.actualLink = actualLink;
	}

	public String getShortenedLink() {
		return shortenedLink;
	}

	public void setShortenedLink(String shortenedLink) {
		this.shortenedLink = shortenedLink;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
