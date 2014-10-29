package mysword.utils;

import mysword.bean.common.FileBean;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class JFTP {

	public static HashMap<String, Object> createFolder(String hostname,
			int port, String username, String password, String directory,
			boolean force) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ListOrderedMap logs = new ListOrderedMap();
		FTPClient ftpclient = null;

		String[] folders = StringUtils.split(directory, "/");
		try {
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			logs.put("connect " + hostname + ":" + port,
					ftpclient.getReplyString());
			ftpclient.login(username, password);
			logs.put("login username: " + username, ftpclient.getReplyString());
			if (force) {
				for (String temp_folder : folders) {
					if (StringUtils.isNotEmpty(temp_folder)) {
						ftpclient.cwd(temp_folder);
						logs.put("cd " + temp_folder,
								ftpclient.getReplyString());
						if (ftpclient.getReplyCode() == 550) {
							ftpclient.mkd(temp_folder);
							logs.put("create " + temp_folder,
									ftpclient.getReplyString());
							ftpclient.cwd(temp_folder);
						}
					}
				}
				result.put("result", true);
			} else {
				ftpclient.mkd(directory);
				logs.put("create " + directory, ftpclient.getReplyString());
				if (ftpclient.getReplyCode() == 257) {
					result.put("result", true);
				} else {
					result.put("result", false);
				}
			}
		} catch (Exception e) {
			result.put("result", false);
			throw e;
		} finally {
			ftpclient.disconnect();
			logs.put("disconnect", "Disconnect from server.");
		}
		result.put("logs", logs);
		return result;
	}
	
	public static boolean setExecute(String hostname,
			int port, String username, String password, String directory,
			String filename) throws Exception {
		if(StringUtils.isEmpty(directory)) {
			directory = "";
		}
		String filePath = (directory.endsWith("/")?directory+filename:directory+"/"+filename);
		return setExecute(hostname, 21, username, password, filePath);
	}
	
	public static boolean setExecute(String hostname,
			int port, String username, String password, String filePath) throws Exception {
		boolean result = false;
		FTPClient ftpclient = null;
		try{
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			ftpclient.login(username, password);
			result = ftpclient.doCommand("SITE CHMOD", "744 " + filePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			ftpclient.disconnect();
		}
		return result;
	}

	public static HashMap<String, Object> download(String hostname, int port,
			String username, String password, String folder, String filename)
			throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ListOrderedMap logs = new ListOrderedMap();
		FTPClient ftpclient = null;

		try {
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			logs.put("connect " + hostname + ":" + port,
					ftpclient.getReplyString());
			ftpclient.login(username, password);
			logs.put("login username: " + username, ftpclient.getReplyString());
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			result.put("file",
					ftpclient.retrieveFileStream(folder + "/" + filename));
			logs.put("download " + folder + "/" + filename,
					ftpclient.getReplyString());
			if (ftpclient.getReplyCode() == 150) {
				result.put("result", true);
			} else {
				result.put("result", false);
			}
		} catch (Exception e) {
			result.put("result", false);
			throw e;
		} finally {
			ftpclient.disconnect();
			logs.put("disconnect", "Disconnect from server.");
		}
		result.put("logs", logs);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isFileExsit(String hostname, int port,
			String username, String password, String filePath)
			throws Exception {
		ArrayList<FileBean> fileList = (ArrayList<FileBean>)list(hostname, port, username,
				password, filePath).get("files");
		if(fileList != null && fileList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isFileExsit(String hostname, int port,
			String username, String password, String directory, String filename)
			throws Exception {
		String filepath = (directory.endsWith("/") ? (directory + filename)
				: (directory + "/" + filename));
		return isFileExsit(hostname, port, username, password, filepath);
	}

	public static boolean isFolderExsit(String hostname, int port,
			String username, String password, String folder) throws Exception {
		boolean result = false;
		FTPClient ftpclient = null;
		try {
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			ftpclient.login(username, password);
			ftpclient.cwd(folder);
			if (ftpclient.getReplyCode() == 250) {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ftpclient.disconnect();
		}
		return result;
	}

	public static ArrayList<FileBean> ftpFileToBean(FTPFile[] fileList) {
		ArrayList<FileBean> fileBean = new ArrayList<FileBean>();
		for (FTPFile file : fileList) {
			FileBean fb = new FileBean();
			fb.setUsername(file.getUser());
			fb.setDirectory(file.isDirectory());
			fb.setFile(file.isFile());
			fb.setFileGroup(file.getGroup());
			fb.setFilename(file.getName());
			fb.setFileSize(file.getSize());
			fb.setFileTimestamp(file.getTimestamp());
			fb.setUnknown(file.isUnknown());
			fileBean.add(fb);
		}
		return fileBean;
	}

	public static HashMap<String, Object> list(String hostname, int port,
			String username, String password, String directory, String pattern)
			throws Exception {
		String filepath = (directory.endsWith("/") ? (directory + pattern)
				: (directory + "/" + pattern));
		return list(hostname, port, username, password, filepath);
	}

	public static HashMap<String, Object> list(String hostname, int port,
			String username, String password, String filepath) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ListOrderedMap logs = new ListOrderedMap();
		FTPClient ftpclient = null;
		try {
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			logs.put("connect", ftpclient.getReplyString());
			ftpclient.login(username, password);
			logs.put("login", ftpclient.getReplyString());
			ArrayList<FileBean> fileBean = ftpFileToBean(ftpclient
					.listFiles(filepath));
			logs.put("cdls " + filepath, ftpclient.getReplyString());
			result.put("files", fileBean);
			result.put("result", true);
		} catch (Exception e) {
			throw e;
		} finally {
			ftpclient.disconnect();
			logs.put("disconnect", "Disconnect from server.");
		}
		result.put("logs", logs);
		return result;
	}
	
	public static HashMap<String, Object> upload(String hostname, int port,
			String username, String password, String directory, String filename,
			InputStream is) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ListOrderedMap logs = new ListOrderedMap();
		FTPClient ftpclient = null;

		try {
			ftpclient = new FTPClient();
			ftpclient.connect(hostname, port);
			logs.put("connect " + hostname + ":" + port,
					ftpclient.getReplyString());
			ftpclient.login(username, password);
			logs.put("login username: " + username, ftpclient.getReplyString());
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpclient.storeFile(directory + "/" + filename, is);
			logs.put("upload " + directory + "/" + filename,
					ftpclient.getReplyString());
			if (ftpclient.getReplyCode() == 226) {
				result.put("result", true);
			} else {
				result.put("result", false);
			}
		} catch (Exception e) {
			result.put("result", false);
			throw e;
		} finally {
			ftpclient.disconnect();
			logs.put("disconnect", "Disconnect from server.");
		}
		result.put("logs", logs);
		return result;
	}

}
