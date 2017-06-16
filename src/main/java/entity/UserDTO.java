package entity;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.kohsuke.github.GHUser;

/**
 * Represent a Github user
 * @author Geoffrey
 *
 */
public class UserDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6680816195877628179L;

	private int id;
	
	private String name;
	
	private String login;
	
	private URL htmlUrl;
	
	public UserDTO(){
		
	}

	public UserDTO(GHUser user) {
		this.htmlUrl = user.getHtmlUrl();
		
		try {
			this.name = user.getName();
		} catch (IOException e) {
			this.name = null;
		}
		this.login = user.getLogin();
		this.id = user.getId();
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getLogin() {
		return login;
	}

	public final void setLogin(String login) {
		this.login = login;
	}

	public final URL getHtmlUrl() {
		return htmlUrl;
	}

	public final void setHtmlUrl(URL htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

}
