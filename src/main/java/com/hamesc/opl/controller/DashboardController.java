package com.hamesc.opl.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hamesc.opl.service.GithubService;
import com.hamesc.opl.utils.ConstantUtils;

@Controller
public class DashboardController {

	@Autowired
	private GithubService githubService;
	
	Logger logger = Logger.getLogger(DashboardController.class);

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request,
			@SessionAttribute(required=true, name= ConstantUtils.ID_SESSION_USERGIT) GitHubClient client,
			@SessionAttribute(required=false, name=ConstantUtils.ID_SESSION_COMMITS) List<RepositoryCommit> listCommit,
			@SessionAttribute(required=false, name=ConstantUtils.ID_SESSION_REPOS) List<Repository> repos,
			ModelMap model) {
		if(client != null) {
			logger.info("Redirection vers l'index dashboard");
			RepositoryService service = new RepositoryService(client);
			try {
				if(repos == null) {
					repos = service.getRepositories();
					request.getSession().setAttribute(ConstantUtils.ID_SESSION_REPOS,repos);
				}
			} catch (IOException e) {
				logger.info("Impossible de lister les repositories, authent fausse, redirection login");
				return "redirect:/login";
			}
			if(listCommit == null) {
				List<RepositoryCommit> rcs = githubService.getAllCommitFromAllProject(client, repos);
				request.getSession().setAttribute(ConstantUtils.ID_SESSION_COMMITS, rcs);
			}
			return "dashboard";
		} else {
			logger.info("Redirection vers la page de login, dashboard sans session");
			return "login";
		}
	}
	@RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
	public ModelAndView showDetails(ModelMap model,
			@SessionAttribute(required=true, name=ConstantUtils.ID_SESSION_USERGIT) GitHubClient client,
			@RequestParam(required=true, name="name") String projectName,
			HttpSession session,
			HttpServletRequest request){
		logger.info("Redirection vers le detail d'un projet");
		RepositoryId repoId = new RepositoryId(client.getUser(), projectName);
		ModelAndView mv = new ModelAndView("projectDetails");
		try {
			List<RepositoryCommit> prs = githubService.getAllCommitFromProject(client, repoId);
			logger.info("La taille de commits : " + prs.size());
			mv.addObject("commits",prs);
			logger.info("Commites trouves pour ce repository : " + projectName);
		} catch (IOException e) {
			logger.info("Pas de commits pour ce repository : " + projectName);
		}
		return mv;
	}
}
