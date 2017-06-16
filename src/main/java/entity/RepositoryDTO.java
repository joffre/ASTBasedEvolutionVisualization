package entity;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;

import org.kohsuke.github.GHRepository;

/**
 * Represents a User repository
 * @author Geoffrey
 *
 */
public class RepositoryDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -243081842333125835L;

	private int id;
	
	private String name;
	
	private String description;
	
	private UserDTO owner;
	
	private URL htmlUrl;
	
	private Date createdAt;
	
	private Date pushedAt;
	
	private String language;
	
	private String fullName;
	
	public RepositoryDTO(){
		
	}
	
	/**
	 * @param repository
	 */
	public RepositoryDTO(GHRepository repository){
			this.fullName = repository.getFullName();
			this.id = repository.getId();
			this.description = repository.getDescription();
			this.name = repository.getName();
			try {
				this.owner = new UserDTO(repository.getOwner());
			} catch (IOException e1) {
				this.owner = null;
			}
			this.htmlUrl = repository.getHtmlUrl();
			try {
				this.createdAt = repository.getCreatedAt();
			} catch (IOException e) {
				this.createdAt = null;
			}
			
			this.pushedAt = repository.getPushedAt();
			
			this.language = repository.getLanguage();
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

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final UserDTO getOwner() {
		return owner;
	}

	public final void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public final URL getHtmlUrl() {
		return htmlUrl;
	}

	public final void setHtmlUrl(URL htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public final Date getCreatedAt() {
		return createdAt;
	}

	public final void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public final Date getPushedAt() {
		return pushedAt;
	}

	public final void setPushedAt(Date pushedAt) {
		this.pushedAt = pushedAt;
	}

	public final String getLanguage() {
		return language;
	}

	public final void setLanguage(String language) {
		this.language = language;
	}

	public final String getFullName() {
		return fullName;
	}

	public final void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
