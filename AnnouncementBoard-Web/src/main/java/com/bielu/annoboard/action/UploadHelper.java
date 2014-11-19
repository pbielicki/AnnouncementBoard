package com.bielu.annoboard.action;

import static com.opensymphony.xwork2.ActionContext.getContext;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.bielu.annoboard.domain.User;

final class UploadHelper {
	
	private static final Log LOG = LogFactory.getLog(UploadHelper.class);

	protected static void moveUploadedFilesToDestDir(List<File> upload, List<String> uploadFileNames) {
		String path = ServletActionContext.getServletContext().getRealPath("images/");
		User creator = (User) getContext().getSession().get(User.class.getName());
		File dir = new File(String.format("%s/%d/", path, creator.getId()));
		if (dir.exists() == false) {
			LOG.debug(String.format("Trying to create [%s] directory.", dir));
			if (dir.mkdirs() == false) {
				throw new IllegalStateException(String.format("Directory [%s] could not be created", dir));
			}
		}
		
		for (int i = 0; i < upload.size(); i++) {
			File renameTo = findAvailableName(dir.getAbsolutePath(), uploadFileNames.get(i));
			uploadFileNames.set(i, renameTo.getName());
			
			if (upload.get(i).renameTo(renameTo) == false) {
				if (renameTo.exists() == false) {
					throw new IllegalStateException(String.format("File [%s] could not be renamed to [%s]", 
							upload.get(i), renameTo));
				}
			}
		}
	}
	
	private static File findAvailableName(String absolutePath, String fileName) {
		File renameTo = new File(String.format("%s/%s", absolutePath, fileName));
		if (renameTo.exists() == false) {
			return renameTo;
		}
		
		int i = 1;
		while (renameTo.exists()) {
			renameTo = new File(String.format("%s/%d_%s", absolutePath, i, fileName));
			i++;
		}
		return renameTo;
	}

	private UploadHelper() {
	}
}
