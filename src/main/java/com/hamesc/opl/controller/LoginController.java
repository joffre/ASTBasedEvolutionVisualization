package com.hamesc.opl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hamesc.opl.utils.ConstantUtils;

@Controller
public class LoginController {

	@RequestMapping("/")
	String home() {
		return "login";
	}

	Logger logger = Logger.getLogger(LoginController.class);

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
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) {
		logger.info("login");
		return "login";
	}

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
			GitHubClient client = new GitHubClient();
			client.setCredentials(username, password);
			request.getSession().setAttribute(ConstantUtils.ID_SESSION_USERGIT, client);
			return "redirect:/dashboard";
		}
	}

	@RequestMapping(value = "/callBackGit", method = RequestMethod.GET)
	public String callBackGit(HttpServletRequest request, 
			ModelMap model) {
		// On recupère le code de github
		request.getSession().setAttribute(ConstantUtils.ID_CODE_FROM_GITHUB,request.getParameter("code"));
		return "dashboard";
	}
	@RequestMapping(value= "/authentGit", method = RequestMethod.GET)
	public String authentGit(HttpServletRequest request, 
			ModelMap model) {
		String clientId = request.getParameter("client_id");
		String secretId = request.getParameter("secret_id");
		request.getSession().setAttribute(ConstantUtils.ID_SESSION_CLI_GITHUB, clientId);
		request.getSession().setAttribute(ConstantUtils.ID_SESSION_SECRET_GITHUB, secretId);
		// Pour authent il faut renvoyer le lien github
		return "dashboard";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, ModelMap model) {

		//Clean cookies TODO

		request.getSession().invalidate();

		return "login";
	}
}
