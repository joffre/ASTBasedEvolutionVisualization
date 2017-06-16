package entity;

import java.io.Serializable;
import java.net.URL;

import org.kohsuke.github.GHCommit.File;

/**
 * Represents a commit file
 * @author Geoffrey
 *
 */
public class FileDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2913616345827859299L;

	private String fileName;
	
	private int linesAdded;
	
	private int linesChanged;
	
	private int linesDeleted;
	
	private String status;
	
	private URL rawUrl;
	
	private String previousFilename;
	
	private URL blobUrl;
	
	private String appFileName;
	
	private String fileExtension;
	
	public FileDTO(){
		
	}

	/**
	 * @param ghFile
	 */
	public FileDTO(File ghFile) {
		this.fileName = ghFile.getFileName();
		
		this.linesAdded = ghFile.getLinesAdded();
		
		this.linesChanged = ghFile.getLinesChanged();
		
		this.linesDeleted = ghFile.getLinesDeleted();
		
		this.status = ghFile.getStatus();
		
		this.rawUrl = ghFile.getRawUrl();
		
		this.previousFilename = ghFile.getPreviousFilename();
		
		this.blobUrl = ghFile.getBlobUrl();
		
		this.setFileExtension("");
		int pointIndex = getFileName().lastIndexOf('.');
		if(pointIndex != -1 && pointIndex < getFileName().length()-1){
			this.setFileExtension(getFileName().substring(pointIndex));
		}
		
		
	}

	public final String getFileName() {
		return fileName;
	}

	public final void setFileName(String fileName) {
		this.fileName = fileName;
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

	public final String getStatus() {
		return status;
	}

	public final void setStatus(String status) {
		this.status = status;
	}

	public final URL getRawUrl() {
		return rawUrl;
	}

	public final void setRawUrl(URL rawUrl) {
		this.rawUrl = rawUrl;
	}

	public final String getPreviousFilename() {
		return previousFilename;
	}

	public final void setPreviousFilename(String previousFilename) {
		this.previousFilename = previousFilename;
	}

	public final URL getBlobUrl() {
		return blobUrl;
	}

	public final void setBlobUrl(URL blobUrl) {
		this.blobUrl = blobUrl;
	}

	public final String getAppFileName() {
		return appFileName;
	}

	public final void setAppFileName(String appFileName) {
		this.appFileName = appFileName;
	}

	public final String getFileExtension() {
		return fileExtension;
	}

	public final void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

}
