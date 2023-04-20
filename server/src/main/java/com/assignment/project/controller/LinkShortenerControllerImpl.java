package com.assignment.project.controller;
import com.assignment.project.modelRespository.LinkRepository;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.project.services.IdentifierGeneratorService;
import com.assignment.project.services.IdentifierGeneratorServiceImpl;
import com.assignment.project.services.LinkHashService;
import com.assignment.project.services.LinkHashServiceImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.project.model.Links;

@RestController
public class LinkShortenerControllerImpl implements LinkShortenerController{

	@Autowired
	private LinkRepository linkRepository;
	@Autowired
	private IdentifierGeneratorService idGeneratorService;
	@Autowired
	private LinkHashService linkHashService;
	private Links urlObject;

	private static Map<String, String> shortenLinkResponse = new HashMap<>();

	@RequestMapping(value = "/api/shortenLink",method = RequestMethod.POST)
	public ResponseEntity<Object> shortenLinkRecieved(@RequestBody Map<String,String> urlInput) throws Exception{ 
		String actualURL = urlInput.get("longUrl");

		try {
			new URL(actualURL).toURI();
		} catch (MalformedURLException e) {
			return new ResponseEntity<>("ImporperURL", HttpStatus.OK);
		} catch (URISyntaxException e) {
			return new ResponseEntity<>("InvalidURL", HttpStatus.OK);
		}

		if (linkRepository.findByactualLink(actualURL)!=null) {
			urlObject = linkRepository.findByactualLink(actualURL); 
		}else {
			urlObject = new Links();		 
			urlObject.setActualLink(actualURL);
			urlObject.setPkLinkID(idGeneratorService.generateSequence(Links.SEQUENCE_NAME));
			String transformedURL = linkHashService.pkToLinkConverter( urlObject.getPkLinkID());
			urlObject.setShortenedLink("https://shortenedURL.is/"+transformedURL);
			linkRepository.save(urlObject);

		}
		return new ResponseEntity<>(urlObject.getShortenedLink(), HttpStatus.OK);
	}
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/redirect",method = RequestMethod.POST)
	public ResponseEntity<Object> getAndRedirecttoShortURL(@RequestBody Map<String,String> shortUrlData) {
		Links redirectUrlobj = linkHashService.getOriginalUrl(shortUrlData.get("url"));

		if (redirectUrlobj!=null && !redirectUrlobj.isExpired()) {
			return new ResponseEntity<>(redirectUrlobj.getActualLink(), HttpStatus.OK);
		}else if (redirectUrlobj!=null && redirectUrlobj.isExpired()){
			return new ResponseEntity<>("Expired", HttpStatus.OK);

		}else{
			return new ResponseEntity<>("Url mapping not found", HttpStatus.OK);

		}

	}

}
