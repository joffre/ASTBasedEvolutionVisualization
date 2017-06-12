package com.hamesc.opl.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Service to manage user's commits
 * @author Geoffrey
 *
 */
@Component
public class ARepositoryCommitService {

	Logger logger = Logger.getLogger(ARepositoryCommitService.class);
	
	@Autowired
	StorageService storageService;

	/**
	 * Load all commits by user's repos
	 * @param user
	 * @param repos
	 * @return List<RepositoryCommit>
	 */
	public List<RepositoryCommit> getAllCommitFromAllProject(GitHubClient user, List<Repository> repos) {
		
		RepositoryId repoId;
		List<RepositoryCommit> allCommitList = new ArrayList<RepositoryCommit>();
		for(int i = 0 ; i < repos.size() ; i ++) {
			repoId = new RepositoryId(user.getUser(), repos.get(i).getName());
			try {
				allCommitList.addAll(getAllCommitFromProject(user, repoId));
				logger.info("Commits trouvés pour ce repository : " + repos.get(i).getName());
			} catch (IOException e) {
				logger.info("Pas de commits pour ce repository : " + repos.get(i).getName());
				logger.info("Suivant..");
			}
		}
		logger.info("Commits trouves : " + allCommitList.size());
		return allCommitList;
	}
	
	/**
	 * Load commits from project
	 * @param user
	 * @param repoId
	 * @return List<RepositoryCommit>
	 * @throws IOException
	 */
	public List<RepositoryCommit> getAllCommitFromProject(GitHubClient user, RepositoryId repoId) throws IOException{
		
		List<RepositoryCommit> commits = new ArrayList<RepositoryCommit>();
		
		File repoCommits = new File(user.getUser()+ "_" + repoId.getName() + ".txt");
		logger.debug("Commits file path : " + repoCommits.getAbsolutePath());
		if(repoCommits.exists() && Files.size(repoCommits.toPath()) > 0){
			commits.addAll(loadCommitsFromJsonFile(repoCommits.getName()));
		} else {
			repoCommits.createNewFile();
			CommitService commitService = new CommitService();
			commits.addAll(commitService.getCommits(repoId));
		}
		return commits;
	}
	
	/**
	 * Store commits in json file in app's storage
	 * @param fileName
	 * @param commits
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void saveCommitsToJsonFile(String fileName, List<RepositoryCommit> commits) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(storageService.getFile(fileName), commits);
	}

	/**
	 * Load commits from json file located app's storage
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public List<RepositoryCommit> loadCommitsFromJsonFile(String filename) throws IOException{
		List<RepositoryCommit> commits  = new ArrayList<RepositoryCommit>();
		ObjectMapper objectMapper = new ObjectMapper();
		commits.addAll((Collection<RepositoryCommit>) objectMapper.readValue(storageService.getFile(filename), new TypeReference<List<RepositoryCommit>>(){}));
		
		return commits; 
	}
}
