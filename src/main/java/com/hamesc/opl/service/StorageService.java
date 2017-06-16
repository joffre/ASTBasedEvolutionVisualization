package com.hamesc.opl.service;

import java.io.File;

import org.springframework.stereotype.Service;

/**
 * Service to manage app's filestorage
 * @author Geoffrey
 *
 */
@Service
public class StorageService {

	/**
	 * Get current user directory path
	 * @return
	 */
	public String getUserDirectory() {
		return System.getProperty("user.dir");
	}

	/**
	 * Get app's common directory path
	 * @return String
	 */
	public String getASTBEVDirectory() {
		return getUserDirectory() + File.separator + ".ASTBEV";
	}

	/**
	 * Get app's common directory file
	 * @return File
	 */
	public File getASTBEVDirectoryFile() {
		File folder = new File(getASTBEVDirectory());
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder;
	}

	/**
	 * Get a file from app's common directory
	 * @param name
	 * @param extension
	 * @return File
	 */
	public File getFile(String name, String extension) {
		return new File(getASTBEVDirectoryFile(), name + extension);
	}
	
	/**
	 * Get a file from app's common directory
	 * @param name
	 * @param extension
	 * @return File
	 */
	public File getFile(String pathInCommonDirectory, String name, String extension) {
		
		String parentFolderPath = getASTBEVDirectory()+File.separator+pathInCommonDirectory;
		File parentFolder = new File(parentFolderPath);
		if(!parentFolder.exists()) parentFolder.mkdirs();
		return new File(parentFolder, name + extension);
	}

	/**
	 * Get a file from app's common directory
	 * @param name
	 * @return File
	 */
	public File getFile(String name) {
		if (name.indexOf(".") != -1)
			return getFile(name, "");
		
		return getFile(name, ".txt");
	}
}
