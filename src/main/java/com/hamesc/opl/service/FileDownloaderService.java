package com.hamesc.opl.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Service to manage user's files
 * @author Geoffrey
 *	
 */
@Service
public class FileDownloaderService {
	
	Logger logger = Logger.getLogger(FileDownloaderService.class);	
	
	public boolean getFile(URL url, File file){
		try {
			FileUtils.copyURLToFile(url, file);
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
