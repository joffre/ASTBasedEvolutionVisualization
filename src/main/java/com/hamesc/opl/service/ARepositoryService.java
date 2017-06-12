package com.hamesc.opl.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Service to manage user's repositories
 * @author Geoffrey
 *
 */
@Service
public class ARepositoryService {
	
	Logger logger = Logger.getLogger(ARepositoryService.class);
	
	@Autowired
	StorageService storageService;

	/**
	 * Load all user's repositories
	 * @param client
	 * @return List<Repository>
	 */
	public List<Repository> getRepositories(GitHubClient client) {
		
		List<Repository> repositories  = new ArrayList<Repository>();
		
		RepositoryService service = new RepositoryService(client);
		try {
			String user = client.getUser();
			
			List<Repository> storedRepositories = loadReposFromJsonFile(user);
			
			if(storedRepositories == null || storedRepositories.isEmpty()){
				repositories.addAll(service.getRepositories());
				saveReposToJsonFile(user, repositories);
			} else {
				repositories.addAll(storedRepositories);
			}
		} catch (IOException e) {
			logger.info("Impossible de lister les repositories, authent fausse, redirection login");
			return null;
		}
		return repositories;
	}
	
	/**
	 * Store repositories in json file in app's storage
	 * @param fileName
	 * @param repos
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void saveReposToJsonFile(String fileName, List<Repository> repos) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(storageService.getFile(fileName), repos);
	}
	
	/**
	 * Load repositories from json file located app's storage
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public List<Repository> loadReposFromJsonFile(String filename) throws IOException{
		List<Repository> repositories  = new ArrayList<Repository>();
		File jsonFile = storageService.getFile(filename);
		if(!jsonFile.exists()) return null;
		ObjectMapper objectMapper = new ObjectMapper();
		repositories.addAll((Collection<Repository>) objectMapper.readValue(jsonFile, new TypeReference<List<Repository>>(){}));
		return repositories; 
	}
}
