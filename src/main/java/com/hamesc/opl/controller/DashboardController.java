package com.hamesc.opl.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hamesc.opl.service.ARepositoryCommitService;
import com.hamesc.opl.service.ARepositoryService;
import com.hamesc.opl.service.DiffService;
import com.hamesc.opl.utils.ConstantUtils;

import entity.CommitDTO;
import entity.RepositoryDTO;

/**
 * Main page controller
 * @author Geoffrey
 *
 */
@Controller
public class DashboardController {

	@Autowired
	private ARepositoryCommitService repositoryCommitService;
	
	@Autowired
	private ARepositoryService repositoryService;
	
	@Autowired
	private DiffService diffService;
	
	Logger logger = Logger.getLogger(DashboardController.class);

	/**
	 * Send to dashboard
	 * @param request
	 * @param github
	 * @param listCommit
	 * @param repos
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request,
			@SessionAttribute(required=true, name= ConstantUtils.ID_SESSION_USERGIT) GitHub github,
			@SessionAttribute(required=false, name=ConstantUtils.ID_SESSION_COMMITS) List<CommitDTO> listCommit,
			@SessionAttribute(required=false, name=ConstantUtils.ID_SESSION_REPOS) List<RepositoryDTO> repos,
			ModelMap model) {
		if(github != null) {
			logger.info("Redirection vers l'index dashboard");
			try {
				logger.warn(github.rateLimit().toString()+"");
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			if(repos == null || repos.isEmpty()) {
				repos = repositoryService.getRepositories(github);
				request.getSession().setAttribute(ConstantUtils.ID_SESSION_REPOS,repos);
			}
			
			if(repos != null){
				if(listCommit == null) {
					List<CommitDTO> rcs = repositoryCommitService.getAllCommitFromAllProject(github, repos);
					request.getSession().setAttribute(ConstantUtils.ID_SESSION_COMMITS, rcs);
				}
			} else {
				logger.info("Redirection vers la page de login, aucun repos r�cup�r�s");
				return "login";
			}
			return "dashboard";
		} else {
			logger.info("Redirection vers la page de login, dashboard sans session");
			return "login";
		}
	}
	/**
	 * Send to a project details page
	 * @param model
	 * @param github
	 * @param projectName
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
	public ModelAndView showDetails(ModelMap model,
			@SessionAttribute(required=true, name=ConstantUtils.ID_SESSION_USERGIT) GitHub github,
			@RequestParam(required=true, name="name") String projectName,
			HttpSession session,
			HttpServletRequest request){
		logger.info("Redirection vers le detail d'un projet");
		
		ModelAndView mv = new ModelAndView("projectDetails");
		try {
			RepositoryDTO repository = repositoryService.getRepositoryByName(github.getMyself().getLogin(), projectName);
			List<CommitDTO> commits =  (repository != null)?repositoryCommitService.getAllCommitFromProject(github, repository):new ArrayList<CommitDTO>();
			logger.info("Commits' number : " + commits.size());
			mv.addObject("commits",commits);
			logger.info("Commits found for repository : " + projectName);
			
			repositoryCommitService.loadAllCommitFiles(repository, commits);
			logger.info("Commits found for repository '" + projectName+"' loaded.");
			
			diffService.compareAll(repository, commits);
		} catch (IOException e) {
			logger.info("Pas de commits pour ce repository : " + projectName);
			logger.error(e.getMessage(),e);
		}
		return mv;
	}
}
