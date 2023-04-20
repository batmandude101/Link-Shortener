package com.assignment.project.modelRespository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.assignment.project.model.Links;

@Repository
public interface LinkRepository extends MongoRepository<Links,Long>{

	public Links findByShortenedLink(String shortenedLink);
	
	public Links findByactualLink(String actualLink);

}
