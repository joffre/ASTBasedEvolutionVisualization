package com.hamesc.opl.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import entity.CommitDTO;
import entity.DTOParser;
import entity.FileDTO;
import entity.RepositoryDTO;

/**
 * Service to manage user's commits
 * @author Geoffrey
 *
 */
@Service
public class ARepositoryCommitService {

	Logger logger = Logger.getLogger(ARepositoryCommitService.class);
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	FileDownloaderService fileDownloaderService;

	/**
	 * Load all commits by user's repos
	 * @param user
	 * @param repos
	 * @return List<RepositoryCommit>
	 */
	public List<CommitDTO> getAllCommitFromAllProject(GitHub github, List<RepositoryDTO> repos) {
		
		List<CommitDTO> allCommitList = new ArrayList<CommitDTO>();
		for(RepositoryDTO repo : repos) {
			try {
				allCommitList.addAll(getAllCommitFromProject(github, repo));
				logger.info("Repository's commits found : " + repo.getName());
			} catch (IOException e) {
				logger.info("No commit founds for repository : " + repo.getName());
				logger.error(" ==> Cause : " + e.getMessage(), e);
				logger.info("Next..");
			}
		}
		logger.info("Commits found : " + allCommitList.size());
		return allCommitList;
	}
	
	/**
	 * Load commits from project
	 * @param user
	 * @param repoId
	 * @return List<RepositoryCommit>
	 * @throws IOException
	 */
	public List<CommitDTO> getAllCommitFromProject(GitHub github, RepositoryDTO repository) throws IOException {
		
		List<CommitDTO> commits = new ArrayList<CommitDTO>();
		List<CommitDTO> repoCommits = loadCommitsFromJsonFile(github.getMyself().getLogin()+ "_" + repository.getName() + ".txt");
		if(repoCommits != null && !repoCommits.isEmpty()){
			commits.addAll(repoCommits);
		} else {
			if(repository.getCreatedAt().getTime() != repository.getPushedAt().getTime()){
				//logger.warn(repository.getName() + " < C: " + repository.getCreatedAt().getTime() + ", P: " + repository.getPushedAt().getTime());
				try {
					GHRepository ghRepository = github.getRepository(repository.getFullName());
					PagedIterable<GHCommit> webcommits = ghRepository.listCommits();
					if(webcommits != null) commits.addAll(DTOParser.parseCommitList(webcommits.	asList()));
					saveCommitsToJsonFile(github.getMyself().getLogin()+ "_" + repository.getName() + ".txt", commits);
				} catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}
		return commits;
	}
	
	/**
	 * Download all commit files if it's not already did
	 * @param commit
	 */
	public void loadAllCommitFiles(RepositoryDTO repository, CommitDTO commit){
		for(FileDTO commitFile : commit.getFiles()){
			String newFileName = "";
			
			File fileToLoad = storageService.getFile(repository.getName(), commitFile.getAppFileName(), "");
			
			if(!fileToLoad.exists()){
				if(fileDownloaderService.getFile(commitFile.getRawUrl(), fileToLoad)){
					logger.info("File '" +fileToLoad.getName()+ "' download successful");
				} else {
					logger.warn("File '" +fileToLoad.getName()+ "' download failed..");
				}
			}
		}
	}
	
	/**
	 * Download all commit files if it's not already did, for all commits
	 * @param commits
	 */
	public void loadAllCommitFiles(RepositoryDTO repository, List<CommitDTO> commits){
		for(CommitDTO commit : commits){
			loadAllCommitFiles(repository, commit);
		}
	}
	
	/**
	 * Store commits in json file in app's storage
	 * @param fileName
	 * @param commits
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void saveCommitsToJsonFile(String fileName, List<CommitDTO> commits) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		logger.warn(commits.size() + " commits save start...");
		objectMapper.writeValue(storageService.getFile(fileName), commits);
	}

	/**
	 * Load commits from json file located app's storage
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public List<CommitDTO> loadCommitsFromJsonFile(String filename) throws IOException{
		List<CommitDTO> commits  = new ArrayList<CommitDTO>();
		ObjectMapper objectMapper = new ObjectMapper();
		
		File fToLoad = storageService.getFile(filename);
		
		if(fToLoad.exists()) commits.addAll((Collection<CommitDTO>) objectMapper.readValue(fToLoad, new TypeReference<List<CommitDTO>>(){}));
		
		return commits; 
	}
}
