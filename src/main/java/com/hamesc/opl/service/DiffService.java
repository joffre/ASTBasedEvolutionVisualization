package com.hamesc.opl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;

import entity.CommitDTO;
import entity.FileDTO;
import entity.RepositoryDTO;
import gumtree.spoon.AstComparator;
import gumtree.spoon.builder.SpoonGumTreeBuilder;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.Operation;
import spoon.reflect.declaration.CtElement;

@Service
public class DiffService {

	Logger logger = Logger.getLogger(DiffService.class);

	@Autowired
	private StorageService storageService;

	/**
	 * Compare common files between commits
	 * 
	 * @param oldCommit
	 * @param newCommit
	 */
	public Map<String, Map<String, Integer>> compareCommit(RepositoryDTO repository, CommitDTO oldCommit,
			CommitDTO newCommit) {

		logger.info("Compare commit : " + oldCommit.getCommitDate().toString() + " & commit : "
				+ newCommit.getCommitDate().toString());
		if (oldCommit.getCommitDate().after(newCommit.getCommitDate())) {
			// exchange commit
		}
		;

		Map<String, Map<String, Integer>> statistics = new HashMap<String, Map<String, Integer>>();

		for (FileDTO oldFile : oldCommit.getFiles()) {
			for (FileDTO newFile : newCommit.getFiles()) {
				if (newFile.getStatus().equalsIgnoreCase("modified")) {
					if (newFile.getFileName().equals(oldFile.getFileName())) {
						if (newFile.getFileExtension().toLowerCase().contains("java")) {
							try {
								Map<String, Map<String, Integer>> compareStats = compareToStats(repository.getName(),
										oldFile, newFile);

								logger.info("Before -I (current) : " + statistics.toString());
								logger.info("Before -I (new) : " + compareStats.toString());

								// Node type (Method, Field, etc.)
								for (String type : compareStats.keySet()) {

									Map<String, Integer> tmpTypeToAdd = compareStats.get(type);
									Map<String, Integer> currentGlobalType = statistics.get(type);

									if (currentGlobalType == null) {
										statistics.put(type, tmpTypeToAdd);
									} else {
										// Action (Add, Delete, Insert, Move,
										// Update)
										for (String action : tmpTypeToAdd.keySet()) {

											int hitNumber = currentGlobalType.getOrDefault(action, 0);
											int hitToAdd = tmpTypeToAdd.getOrDefault(action, 0);
											currentGlobalType.put(action, hitNumber + hitToAdd);
										}

										statistics.put(type, currentGlobalType);
									}
								}

								logger.info("After -I (current) : " + statistics.toString());

							} catch (IOException e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
		return statistics;
	}

	/**
	 * Compare two common files
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws IOException
	 */
	public String compareFiles(String repoName, FileDTO oldFile, FileDTO newFile) throws IOException {
		logger.info("Comparison of " + oldFile.getFileName());

		AstComparator diffTool = new AstComparator();
		try {
			Diff result = diffTool.compare(storageService.getFile(repoName, oldFile.getAppFileName(), ""),
					storageService.getFile(repoName, newFile.getAppFileName(), ""));
			logger.info("Result : " + ((result != null) ? result.getRootOperations().size() : "empty"));

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode actionsArrayNode = mapper.createArrayNode();

			for (Operation op : result.getRootOperations()) {

				Action action = op.getAction();

				CtElement element = (CtElement) action.getNode().getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT);

				ObjectNode jsonAction = mapper.createObjectNode();

				// action name
				jsonAction.put("action", action.getClass().getSimpleName());

				// node type
				String nodeType = element.getClass().getSimpleName();
				nodeType = nodeType.substring(2, nodeType.length() - 4);
				jsonAction.put("nodeType", nodeType);

				ObjectNode actionPositionJSON = mapper.createObjectNode();
				if (element.getPosition() != null) {
					actionPositionJSON.put("line", element.getPosition().getLine());
					actionPositionJSON.put("sourceStart", element.getPosition().getSourceStart());
					actionPositionJSON.put("sourceEnd", element.getPosition().getSourceEnd());
					actionPositionJSON.put("endLine", element.getPosition().getEndLine());

				}
				if (action instanceof Delete || action instanceof Update || action instanceof Move) {
					jsonAction.put("oldLocation", actionPositionJSON);
				} else {
					jsonAction.put("newLocation", actionPositionJSON);
				}

				// action position
				if (action instanceof Move || action instanceof Update) {
					CtElement elementDest = (CtElement) action.getNode()
							.getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT_DEST);

					ObjectNode actionDestPositionJSON = mapper.createObjectNode();
					if (elementDest.getPosition() != null) {
						actionDestPositionJSON.put("line", elementDest.getPosition().getLine());
						actionDestPositionJSON.put("sourceStart", elementDest.getPosition().getSourceStart());
						actionDestPositionJSON.put("sourceEnd", elementDest.getPosition().getSourceEnd());
						actionDestPositionJSON.put("endLine", elementDest.getPosition().getEndLine());
					}
					jsonAction.put("newLocation", actionDestPositionJSON);
				}

				// if all actions are applied on the same node print only the
				// first action
				if (element.equals(element) && action instanceof Update) {
					break;
				}
				actionsArrayNode.add(jsonAction);
			}

			logger.info("> Details : " + actionsArrayNode.toString());

			return actionsArrayNode.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Compare two common files
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws IOException
	 */
	public Map<String, Map<String, Integer>> compareToStats(String repoName, FileDTO oldFile, FileDTO newFile)
			throws IOException {
		logger.info("Statistics of comparison of  : " + oldFile.getFileName());

		Map<String, Map<String, Integer>> statsByAction = new HashMap<String, Map<String, Integer>>();
		AstComparator diffTool = new AstComparator();
		try {
			Diff result = diffTool.compare(storageService.getFile(repoName, oldFile.getAppFileName(), ""),
					storageService.getFile(repoName, newFile.getAppFileName(), ""));

			Map<String, Integer> hitByNodeType;
			for (Operation op : result.getRootOperations()) {

				Action action = op.getAction();

				CtElement element = (CtElement) action.getNode().getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT);

				CtElement ctElement = result.commonAncestor();

				String actionName = action.getClass().getSimpleName();

				String nodeType = element.getClass().getSimpleName();

				nodeType = nodeType.substring(2, nodeType.length() - 4);

				statsByAction.putIfAbsent(nodeType, new HashMap<String, Integer>());

				hitByNodeType = statsByAction.get(nodeType);

				hitByNodeType.put(actionName, hitByNodeType.getOrDefault(actionName, 0) + 1);

				// if all actions are applied on the same node print only the
				// first action
				if (element.equals(ctElement) && action instanceof Update) {
					break;
				}
				statsByAction.put(nodeType, hitByNodeType);

				// add
			}

			logger.info("> Details : " + statsByAction.toString());

			return statsByAction;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void compareAll(RepositoryDTO repository, List<CommitDTO> commits) {

		for (CommitDTO commit : commits) {
			for (CommitDTO commit2 : commits) {
				if (!commit.getsHA1().equals(commit2.getsHA1())) {
					compareCommit(repository, commit, commit2);
				}
			}
		}
	}

	/**
	 * Compare recursively all commits one after another, both directly side by
	 * side on time line Commit List needs to be ordered by commit date to
	 * obtain best results (ASC or DESC => Detected by method before treatment)
	 * 
	 * @param repository
	 * @param commits
	 * @return Map<String,Map<String, Integer>>
	 */
	public Map<String, Map<String, Integer>> compareRecursively(RepositoryDTO repository, List<CommitDTO> commits) {
		Map<String, Map<String, Integer>> statistics = new HashMap<String, Map<String, Integer>>();

		if (commits.size() >= 2) {

			boolean ascSorted = commits.get(0).getCommitDate().before(commits.get(1).getCommitDate());

			for (int i = 0; i < commits.size() - 1; i++) {
				int indexOld = ascSorted ? i : commits.size() - 1 - i;
				int indexNewer = indexOld + (ascSorted ? 1 : -1);

				Map<String, Map<String, Integer>> compareStats = compareCommit(repository, commits.get(indexOld),
						commits.get(indexNewer));

				logger.info("Before -G (current) : " + statistics.toString());
				logger.info("Before -G (new) : " + compareStats.toString());

				// Node type (Method, Field, etc.)
				for (String type : compareStats.keySet()) {

					Map<String, Integer> tmpTypeToAdd = compareStats.get(type);
					Map<String, Integer> currentGlobalType = statistics.get(type);

					if (currentGlobalType == null) {
						statistics.put(type, tmpTypeToAdd);
					} else {
						// Action (Add, Delete, Insert, Move, Update)
						for (String action : tmpTypeToAdd.keySet()) {

							Integer hitNumber = currentGlobalType.getOrDefault(action, 0);
							Integer hitToAdd = tmpTypeToAdd.getOrDefault(action, 0);
							currentGlobalType.put(action, hitNumber + hitToAdd);
						}

						statistics.put(type, currentGlobalType);
					}
				}

				logger.info("After (new) : " + statistics.toString());
			}
		}

		return statistics;
	}

	public String parseComparatorDataToStringTable(Map<String, Map<String, Integer>> stats) {
		StringBuilder builder = new StringBuilder();

		List<String> actionLabels = getActionLabelsFromComparatorData(stats.values());
		builder.append("[");
		builder.append("'Java Type', ");
		for (int i = 0; i < actionLabels.size(); i++) {
			builder.append("'" + actionLabels.get(i) + "'");
			if (i < actionLabels.size() - 1)
				builder.append(", ");
		}
		builder.append("]");
		builder.append(", ");

		for (Iterator<String> iterator = stats.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			Map<String, Integer> actionValues = stats.get(type);
			builder.append("[");
			builder.append("'" + type + "', ");
			for (int i = 0; i < actionLabels.size(); i++) {
				builder.append("" + actionValues.getOrDefault(actionLabels.get(i), 0) + "");
				if (i < actionLabels.size() - 1)
					builder.append(", ");
			}
			builder.append("]");
			if (iterator.hasNext())
				builder.append(", ");
		}

		return builder.toString();
	}

	public List<String> getActionLabelsFromComparatorData(Collection<Map<String, Integer>> statsActionsValues) {
		List<String> actionLabels = new ArrayList<String>();

		for (Map<String, Integer> actionValues : statsActionsValues) {
			for (String actionName : actionValues.keySet())
				if (!actionLabels.contains(actionName))
					actionLabels.add(actionName);
		}

		return actionLabels;
	}

}
