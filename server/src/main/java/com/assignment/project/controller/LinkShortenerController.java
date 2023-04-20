package com.assignment.project.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface LinkShortenerController {
	public ResponseEntity<Object> shortenLinkRecieved(@RequestBody Map<String,String> urlInput) throws Exception;
	
	public ResponseEntity<Object> getAndRedirecttoShortURL(@RequestBody Map<String,String> shortUrlData);
	
}	
