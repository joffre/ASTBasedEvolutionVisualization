package com.hamesc.opl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hamesc.opl.utils.ConstantUtils;

/**
 * Authentication controller
 * @author Geoffrey
 *
 */
@Controller
public class LoginController {

	/**
	 * Default page on root mapping
	 * @return
	 */
	@RequestMapping("/")
	String home() {
		return "login";
	}

	Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * Github authentication
	 * @param request
	 * @param clientId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginOAuth", method = RequestMethod.GET)
	public String loginOAuth(HttpServletRequest request, 
			@CookieValue(value = "clientId",defaultValue="") String clientId,
			ModelMap model) {
		if(clientId.length() == 0) {
			logger.info("Pas de cookie client ID, redirection vers la mire de login");
			return "loginOAuth";
		} else {
			request.getSession().setAttribute(ConstantUtils.ID_SESSION_CLI_GITHUB, clientId);
			return "dashboard";
		}

	}
	
	/**
	 * Login page redirection
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) {
		logger.info("login");
		return "login";
	}

	/**
	 * Form authentication
	 * @param request
	 * @param model
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String loginPost(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		logger.info("Requete POST recu");
		String username = request.getParameter("usernameGitHub");
		String password = request.getParameter("passwordGitHub");
		logger.info("Username : " + username);
		if(username == null || password == null) {
			logger.info("Username ou password null ! ");
			return "redirect:/login";
		} else {
			GitHub github = GitHub.connectUsingPassword(username, password);
			request.getSession().setAttribute(ConstantUtils.ID_SESSION_USERGIT, github);
			return "redirect:/dashboard";
		}
	}

	/**
	 * Git response on oauth authentication
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/callBackGit", method = RequestMethod.GET)
	public String callBackGit(HttpServletRequest request, 
			ModelMap model) {
		request.getSession().setAttribute(ConstantUtils.ID_CODE_FROM_GITHUB,request.getParameter("code"));
		return "dashboard";
	}
	
	/**
	 * Saves session and secret github client informations
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping(value= "/authentGit", method = RequestMethod.GET)
	public String authentGit(HttpServletRequest request, 
			ModelMap model) {
		String clientId = request.getParameter("client_id");
		String secretId = request.getParameter("secret_id");
		request.getSession().setAttribute(ConstantUtils.ID_SESSION_CLI_GITHUB, clientId);
		request.getSession().setAttribute(ConstantUtils.ID_SESSION_SECRET_GITHUB, secretId);
		return "dashboard";
	}

	/**
	 * Invalidate session and login redirection
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, ModelMap model) {

		request.getSession().invalidate();

		return "login";
	}
}
