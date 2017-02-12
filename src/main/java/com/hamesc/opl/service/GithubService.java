package com.hamesc.opl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.springframework.stereotype.Service;

@Service
public class GithubService {

	Logger logger = Logger.getLogger(GithubService.class);
	
	public List<PullRequest> getAllPullRequestFromAllProject(GitHubClient user, List<Repository> repos) {
		PullRequestService prService = new PullRequestService(user);
		RepositoryId repoId;
		List<PullRequest> allPrList = new ArrayList<PullRequest>();		
		for(int i = 0 ; i < repos.size() ; i ++) {
			repoId = new RepositoryId(user.getUser(), repos.get(i).getName());
			try {
				allPrList.addAll(prService.getPullRequests(repoId, null));
			} catch (IOException e) {
				logger.info("Pas de pull request pour ce repository");
				logger.info("Passage au suivant..");
			}
		}
		logger.info("Pull request trouvé : " + allPrList.size());
		return allPrList;
	}

	public List<RepositoryCommit> getAllCommitFromAllProject(GitHubClient user, List<Repository> repos) {
		CommitService commitService = new CommitService();
		PullRequestService prService = new PullRequestService(user);
		RepositoryId repoId;
		List<RepositoryCommit> allCommitList = new ArrayList<RepositoryCommit>();
		for(int i = 0 ; i < repos.size() ; i ++) {
			repoId = new RepositoryId(user.getUser(), repos.get(i).getName());
			try {
				allCommitList.addAll(commitService.getCommits(repoId));
			} catch (IOException e) {
				logger.info("Pas de commits pour ce repository");
				logger.info("Suivant..");
			}
		}
		logger.info("Commits trouvés : " + allCommitList.size());
		return allCommitList;
	}
}
