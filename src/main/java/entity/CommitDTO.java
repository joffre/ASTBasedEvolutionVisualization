package entity;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kohsuke.github.GHCommit;

/**
 * Represent a commit
 * @author Geoffrey
 *
 */
public class CommitDTO implements Serializable, Comparable<CommitDTO>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4252679285939759874L;

	private UserDTO author;
	
	private int linesAdded;
	
	private int linesChanged;
	
	private int linesDeleted;
	
	private URL htmlUrl;
	
	private String message;
	
	private List<FileDTO> files;
	
	private Date commitDate;
	
	private String sHA1;
	
	public CommitDTO(){
		
	}
	
	/**
	 * @param commit
	 */
	public CommitDTO(GHCommit commit){
		this.sHA1 = commit.getSHA1();
		
		try {
			this.author = (commit.getAuthor() != null)?new UserDTO(commit.getAuthor()):null;
		} catch (IOException e) {
			this.author = null;
		}
		
		try {
			this.linesAdded = commit.getLinesAdded();
		} catch (IOException e) {
			this.linesAdded = -1;
		}
		
		try {
			this.linesChanged = commit.getLinesChanged();
		} catch (IOException e) {
			this.linesChanged = -1;
		}
		
		try {
			this.linesDeleted = commit.getLinesDeleted();
		} catch (IOException e) {
			this.linesDeleted = -1;
		}
		
		this.htmlUrl = commit.getHtmlUrl();
		
		try {
			files = (commit.getFiles() != null)?DTOParser.parseFileList(sHA1, commit.getFiles()):new ArrayList<FileDTO>();
		} catch (IOException e) {
			files = new ArrayList<FileDTO>();
		}
		
		try {
			this.message = commit.getCommitShortInfo().getMessage();
		} catch (IOException e) {
			this.message = "<<Empty>>";
		}
		
		try {
			this.commitDate = commit.getCommitDate();
		} catch (IOException e) {
			this.commitDate = null;
		}
	}

	public final UserDTO getAuthor() {
		return author;
	}

	public final void setAuthor(UserDTO author) {
		this.author = author;
	}

	public final int getLinesAdded() {
		return linesAdded;
	}

	public final void setLinesAdded(int linesAdded) {
		this.linesAdded = linesAdded;
	}

	public final int getLinesChanged() {
		return linesChanged;
	}

	public final void setLinesChanged(int linesChanged) {
		this.linesChanged = linesChanged;
	}

	public final int getLinesDeleted() {
		return linesDeleted;
	}

	public final void setLinesDeleted(int linesDeleted) {
		this.linesDeleted = linesDeleted;
	}

	public final URL getHtmlUrl() {
		return htmlUrl;
	}

	public final void setHtmlUrl(URL htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final List<FileDTO> getFiles() {
		return files;
	}

	public final void setFiles(List<FileDTO> files) {
		this.files = files;
	}

	public final Date getCommitDate() {
		return commitDate;
	}

	public final void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public final String getsHA1() {
		return sHA1;
	}

	public final void setsHA1(String sHA1) {
		this.sHA1 = sHA1;
	}

	@Override
	public int compareTo(CommitDTO o) {
		return this.getCommitDate().compareTo(o.getCommitDate());
	}

}
