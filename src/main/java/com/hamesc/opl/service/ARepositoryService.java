package com.hamesc.opl.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import entity.DTOParser;
import entity.RepositoryDTO;

/**
 * Service to manage user's repositories
 * 
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
	 * 
	 * @param client
	 * @return List<Repository>
	 */
	public List<RepositoryDTO> getRepositories(GitHub github) {

		List<RepositoryDTO> repositories = new ArrayList<RepositoryDTO>();

		try {
			String user = github.getMyself().getLogin();

			List<RepositoryDTO> storedRepositories = loadReposFromJsonFile(user);

			if (storedRepositories == null || storedRepositories.isEmpty()) {

				GHMyself myself = github.getMyself();

				List<GHRepository> currentRepositories = new ArrayList<GHRepository>(myself.getRepositories().values());// repoSB.list().asList();

				repositories.addAll(DTOParser.parseRepositoryList(currentRepositories));

				saveReposToJsonFile(user, repositories);
			} else {
				repositories.addAll(storedRepositories);
			}
		} catch (IOException e) {
			logger.error("Impossible de lister les repositories, authent fausse, redirection login", e);
			return null;
		}
		return repositories;
	}

	/**
	 * Store repositories in json file in app's storage
	 * 
	 * @param fileName
	 * @param repos
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void saveReposToJsonFile(String fileName, List<RepositoryDTO> repos)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		logger.warn(repos.size() + " repos save start...");
		objectMapper.writeValue(storageService.getFile(fileName), repos);
	}

	/**
	 * Load repositories from json file located app's storage
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public List<RepositoryDTO> loadReposFromJsonFile(String filename) throws IOException {
		List<RepositoryDTO> repositories = new ArrayList<RepositoryDTO>();
		File jsonFile = storageService.getFile(filename);
		if (!jsonFile.exists())
			return null;
		ObjectMapper objectMapper = new ObjectMapper();
		repositories.addAll(
				(Collection<RepositoryDTO>) objectMapper.readValue(jsonFile, new TypeReference<List<RepositoryDTO>>() {
				}));
		return repositories;
	}

	/**
	 * Get local repository by name
	 * @param userLogin
	 * @param projectName
	 * @return RepositoryDTO
	 */
	public RepositoryDTO getRepositoryByName(String userLogin, String projectName) {
		try {
			List<RepositoryDTO> storedRepositories = loadReposFromJsonFile(userLogin);

			for (RepositoryDTO repo : storedRepositories) {
				if (repo.getFullName().equalsIgnoreCase(projectName))
					return repo;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Get languages of local user's repositories
	 * @param userLogin
	 * @return List<String>
	 * @throws IOException
	 */
	public List<String> getRepoLanguages(String userLogin) throws IOException {
		List<String> languages = new ArrayList<String>();
		List<RepositoryDTO> repositories = loadReposFromJsonFile(userLogin);

		for (RepositoryDTO repository : repositories) {
			if (StringUtils.hasText(repository.getLanguage()) && !languages.contains(repository.getLanguage()))
				languages.add(repository.getLanguage());
		}

		return languages;
	}
}
