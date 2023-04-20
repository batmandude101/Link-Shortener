package com.assignment.project.services;

import com.assignment.project.model.Links;

public interface LinkHashService {
	public String pkToLinkConverter(long keyForURL);
	
	public Links getOriginalUrl (String shortUrl);
}
