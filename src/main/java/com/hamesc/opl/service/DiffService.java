package com.hamesc.opl.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamesc.opl.utils.ASTDiff;

import entity.CommitDTO;
import entity.FileDTO;
import entity.RepositoryDTO;
import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

@Service
public class DiffService {
	
	Logger logger = Logger.getLogger(DiffService.class);
	
	@Autowired
	private StorageService storageService;
	
	public void getCompilationUnit(GHCommit repoCommit) throws IOException{
		for(File file : repoCommit.getFiles()){
			System.out.println(file.getFileName() + " - Raw url : " + file.getRawUrl());
		}
	}
	
	/**
	 * Compare common files between commits
	 * @param oldCommit
	 * @param newCommit
	 */
	public void compare(RepositoryDTO repository, CommitDTO oldCommit, CommitDTO newCommit){
		if(oldCommit.getCommitDate().after(newCommit.getCommitDate())){
			//exchange commit
		};
		
		for(FileDTO oldFile : oldCommit.getFiles()){
			for(FileDTO newFile : newCommit.getFiles()){
				if(newFile.getStatus().equalsIgnoreCase("modified")){
					if(newFile.getFileName().equals(oldFile.getFileName())){
						if(newFile.getFileExtension().toLowerCase().contains("java")){
							try {
								compare(repository.getName(), oldFile, newFile);
							} catch (IOException e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Compare two common files
	 * @param oldFile
	 * @param newFile
	 * @throws IOException 
	 */
	public void compare(String repoName, FileDTO oldFile, FileDTO newFile) throws IOException{
		logger.debug("Comparison of " + oldFile.getFileName());
		
		AstComparator diffTool = new AstComparator();
		try {
			Diff result = diffTool.compare(storageService.getFile(repoName, oldFile.getAppFileName(), ""), storageService.getFile(repoName, newFile.getAppFileName(), ""));
			logger.info("Result : " + ((result != null)?result.toString():"empty"));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		String oldContent = String.join("\n", Files.readAllLines(storageService.getFile(repoName, oldFile.getAppFileName(), "").toPath()));
		String newContent = String.join("\n", Files.readAllLines(storageService.getFile(repoName, newFile.getAppFileName(), "").toPath()));
		ASTDiff astDiff = new ASTDiff(oldFile.getFileName(), oldContent, newFile.getFileName(), newContent);
		
		logger.info("Result : " + ((astDiff != null)?astDiff:"empty"));
	}

	public void compareAll(RepositoryDTO repository, List<CommitDTO> commits) {
		
		for(CommitDTO commit : commits){
			for(CommitDTO commit2 : commits){
				if(!commit.getsHA1().equals(commit2.getsHA1())){
					compare(repository, commit, commit2);
				}
			}
		}
	}

}
