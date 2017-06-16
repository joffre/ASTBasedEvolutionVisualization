package entity;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHRepository;

/**
 * Entities DTO Parser
 * @author Geoffrey
 *
 */
public class DTOParser {

	/**
	 * Parse a GHFile list and generate a custom app file name to store it later
	 * @param sHA1
	 * @param ghFiles
	 * @return List<FileDTO>
	 */
	public static List<FileDTO> parseFileList(String sHA1, List<File> ghFiles) {
		List<FileDTO> files = new ArrayList<FileDTO>();
		
		for(File ghFile : ghFiles){
			FileDTO file = new FileDTO(ghFile);
			String originFileNameParsed = file.getFileName().replace('/', '-');
			file.setAppFileName(sHA1+"_"+originFileNameParsed);
			files.add(file);
		}
		
		return files;
	}

	/**
	 * Parse a GHCommit list to Data Transfer Object CommitDTO
	 * @param ghCommits
	 * @return List<CommitDTO>
	 */
	public static List<CommitDTO> parseCommitList(List<GHCommit> ghCommits) {
		List<CommitDTO> commits = new ArrayList<CommitDTO>();
		
		for(GHCommit ghCommit : ghCommits){
			CommitDTO commit = new CommitDTO(ghCommit);
			
			commits.add(commit);
		}
		
		return commits;
	}
	
	/**
	 * Parse a GHRepository list to Data Transfer Object RepositoryDTO
	 * @param ghRepositories
	 * @return List<RepositoryDTO>
	 */
	public static List<RepositoryDTO> parseRepositoryList(List<GHRepository> ghRepositories) {
		List<RepositoryDTO> repositories = new ArrayList<RepositoryDTO>();
		
		for(GHRepository ghRepository : ghRepositories){
			RepositoryDTO repository = new RepositoryDTO(ghRepository);
			
			repositories.add(repository);
		}
		
		return repositories;
	}

}
