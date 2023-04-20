package com.assignment.project.services;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.project.model.Links;
import com.assignment.project.modelRespository.LinkRepository;

@Service
public class LinkHashServiceImpl implements LinkHashService{

	@Autowired
	private LinkRepository linkRepository;

	private static String charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int base = 62; 
	
	/**
	 * Converts urls's primary key to it's Base 62 counterpart and sends it back 
	 * @param keyForURL
	 * @return
	 */
	public String pkToLinkConverter(long keyForURL) {
		if (keyForURL < base) {
			return Character.toString(charSet.charAt((int)keyForURL));
		}

		long value = keyForURL;
		StringBuffer generatedURL = new StringBuffer();

		while (value != 0) {
			int remind = (int) (value % base);
			value = (value - remind) / base;
			generatedURL.append(charSet.charAt(remind));
		}

		return generatedURL.toString();
	}

	/**
	 * getsOriginal URL for given shortURL if it is not Expired else return long URL as expired
	 * @param shortUrl
	 * @return
	 */
	public Links getOriginalUrl (String shortUrl) {
		
		Links mappedUrlObj = linkRepository.findByShortenedLink(shortUrl);
		Date currentDateTimeObj = new Date();
		long duration = currentDateTimeObj.getTime() - mappedUrlObj.getCreateTime().getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		if (diffInMinutes > 5) {
			mappedUrlObj.setExpired(true);
		}
		return mappedUrlObj; 
	}
	
	 
}
