package mysword.dao.tools;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import mysword.bean.common.FileBean;
import mysword.system.conf.SystemConf;
import mysword.system.logger.MyLogger;
import mysword.utils.J2SSH;
import mysword.utils.JSFTP;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.net.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MasstestDAO {
	
	public static String getHostname() {
		return SystemConf.getConfString("server.masstest.ip");
	}

	public static String getUsername() {
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.masstest.username"))) {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.masstest.username")));
        } else {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.default.username")));
        }
	}
	
	public static String getPassword() {
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.masstest.password"))) {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.masstest.password")));
        } else {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.default.password")));
        }
	}
	
	public static String getTempFolder() {
		String tempFolder = SystemConf.getConfString("server.masstest.temp.folder");
		if(StringUtils.endsWith(tempFolder, "/")) {
			return tempFolder;
		} else if(StringUtils.isEmpty(tempFolder)) {
			return tempFolder + "./";
		} else {
			return tempFolder + "/";
		}
	}
	
	public static String createMasstess(String processId)
			throws Exception {
		return J2SSH.shell(new String[]{"create_process_folder.sh " + processId}, getHostname(), 22, getUsername(), getPassword());
	}
	
	public static String demoTest(String processId, String... filenames) throws Exception {
		if(filenames == null || filenames.length < 1){
			return "There are no files need to be sent to the application server.";
		}
		String[] commands = new String[filenames.length];
		for(int i=0; i<filenames.length; i++) {
			commands[i] = "./send_dev_new.sh -p " + processId + " " + filenames[i];
		}
		return J2SSH.shell(commands, getHostname(), 22, getUsername(), getPassword());
	}

//	public static String copyTestFileToInbound(String processId, String filename) throws Exception {
//		return J2SSH.shell(new String[]{"./copy_file_inbound.sh " + processId + " " + filename}, hostname, 22, username, password);
//	}
	
	public static String copyTestFileToInbound(String processId, String... filenameList) throws Exception {
		if(filenameList != null && filenameList.length > 0) {
			String[] command = new String[0];
			for(String filename:filenameList) {
				command = ArrayUtils.add(command, "./copy_file_inbound.sh " + processId + " " + filename);
			}
			return J2SSH.shell(command, getHostname(), 22, getUsername(), getPassword());
		} else {
			return "";
		}
	}
	
	public static String copyTestFileToInbound(String processId, List<String> filenameList) throws Exception {
		if(filenameList != null) {
			String[] command = new String[0];
			for(String filename:filenameList) {
				command = ArrayUtils.add(command, "./copy_file_inbound.sh " + processId + " " + filename);
			}
			return J2SSH.shell(command, getHostname(), 22, getUsername(), getPassword());
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<FileBean> getMasstessFileList(String processId, String direction, boolean testfiles) throws Exception {
		if (StringUtils.isEmpty(processId)) {
			return new ArrayList<FileBean>();
		}
		processId = StringUtils.substring(processId, 0, 10);
		ArrayList<FileBean> result = new ArrayList<FileBean>();
		StringBuffer logs = new StringBuffer();
		boolean fileResult = true;
		if("inbound".equals(direction) && testfiles) {
			fileResult = J2SSH.command("./list_process_file.sh -it " + processId, logs, getHostname(), 22, getUsername(), getPassword());
		} else if ("inbound".equals(direction) && !testfiles) {
			fileResult = J2SSH.command("./list_process_file.sh -i " + processId, logs, getHostname(), 22, getUsername(), getPassword());
		} else if ("outbound".equals(direction)) {
			fileResult = J2SSH.command("./list_process_file.sh -o " + processId, logs, getHostname(), 22, getUsername(), getPassword());
		}
		if(!fileResult) {
			MyLogger.sysLogger.info(logs);
			return new ArrayList<FileBean>();
		}
		String[] files = StringUtils.split(logs.toString(), "\n");
		for(String filename : files) {
			FileBean ffb = new FileBean();
			ffb.setFilename(filename);
			result.add(ffb);
		}
		return result;
	}

	public static String uploadFile(String path, String filename, File file) throws Exception {
		if (!file.canRead()) {
			return "File cannot be read!\n";
		}
		if (StringUtils.isEmpty(filename)) {
			filename = file.getName();
		}
		return uploadFile(path, filename, new FileInputStream(file));
	}

	public static String uploadFile(String path, String filename, String content) throws Exception {
		if (StringUtils.isEmpty(content)) {
			content = "";
		}
		return uploadFile(path, filename, new ByteArrayInputStream(content.getBytes()));
	}

	public static String uploadFile(String path, String filename, InputStream is) throws Exception {
		if (StringUtils.isEmpty(path)) {
			path = "./";
		} else if (!StringUtils.endsWith(path, "/")) {
			path += "/";
		}
		if (StringUtils.isEmpty(filename)) {
			filename = DateFormatUtils.format(new Date(), "yyyyMMdd_HHmmssSSS.txt");
		}
		ChannelSftp sftp = JSFTP.getSftp(getHostname(), 22, getUsername(), getPassword());
		sftp.put(is, path+filename);
		JSFTP.disconnet(sftp);
		return "Upload "+path+filename+" successfully!\n";
	}

	public static String uploadInboundFile(String processId, String filename, File file, boolean copyBackup) throws Exception {
		if (!file.canRead()) {
			throw new Exception("File cannot be read!\n");
		}
		if (StringUtils.isEmpty(filename)) {
			filename = file.getName();
		}
		return uploadInboundFile(processId, filename, new FileInputStream(file), copyBackup);
	}

	public static String uploadInboundFile(String processId,
			String filename, String content, boolean copyBackup) throws Exception {
		if(StringUtils.isEmpty(content)) {
			content = "";
		}
		return uploadInboundFile(processId, filename,
				new ByteArrayInputStream(content.getBytes()), copyBackup);
	}

	public static String uploadInboundFile(String processId,
			String filename, InputStream is, boolean copyBackup) throws Exception {
		if (StringUtils.isEmpty(processId)) {
			return "ProcessId is empty!";
		}
		if (StringUtils.isEmpty(filename)) {
			filename = "test.txt";
		}
		String logs = "";
		String content = "";
		String path1 = "dev/" + StringUtils.left(processId, 4)
				+ "/inbound/" + StringUtils.left(processId, 10) + "/";
		String path2 = "dev/"
				+ StringUtils.left(processId, 4)
				+ "/inbound/"
				+ StringUtils.substring(processId, 4) + "/";
		if(is.available()>0) {
			byte[] b = new byte[is.available()];
			is.read(b);
			content = new String(b);
		}
		is.close();
		ChannelSftp sftp = JSFTP.getSftp(getHostname(), 22, getUsername(), getPassword());
		if(JSFTP.existing(sftp, path1)) {
			sftp.put(new ByteArrayInputStream(content.getBytes()), path1 + filename);
			logs = "Upload to " + path1 + filename + " successfully!\n";
			if(copyBackup && JSFTP.existing(sftp, path1 + "testfiles")) {
				sftp.put(new ByteArrayInputStream(content.getBytes()), path1 + "testfiles/" + filename);
				logs += "Upload to " + path1 + "testfiles/" + filename + " successfully!\n";
			}
		} else if(JSFTP.existing(sftp, path2)) {
			sftp.put(new ByteArrayInputStream(content.getBytes()), path2 + filename);
			logs = "Upload to " + path1 + filename + " successfully!\n";
			if(copyBackup && JSFTP.existing(sftp, path2 + "testfiles")) {
				sftp.put(new ByteArrayInputStream(content.getBytes()), path2 + "testfiles/" + filename);
				logs += "Upload to " + path1 + "testfiles/" + filename + " successfully!\n";
			}
		}
		JSFTP.disconnet(sftp);
		return logs;
	}
	
	public static ArrayList<InputStream> downloadFiles(String processId, boolean testfiles, String... filenames) throws JSchException, SftpException {
		ArrayList<InputStream> result = new ArrayList<InputStream>();
		if (StringUtils.isEmpty(processId)) {
			return result;
		}
		if (filenames == null || filenames.length < 1) {
			return result;
		}
		String path1 = "dev/" + StringUtils.mid(processId, 0, 4)
				+ "/inbound/" + processId + "/testfiles";
		String path2 = "dev/"
				+ StringUtils.mid(processId, 0, 4)
				+ "/inbound/"
				+ processId.substring(4) + "/testfiles";
		ChannelSftp sftp = JSFTP.getSftp(getHostname(), 22, getUsername(), getPassword());
		if(JSFTP.existing(sftp, path1)) {
			for(String filename:filenames) {
				result.add(sftp.get(filename));
			}
		} else if(JSFTP.existing(sftp, path2)) {
			for(String filename:filenames) {
				result.add(sftp.get(filename));
			}
		}
		JSFTP.disconnet(sftp);
		return result;
	}
	
	public static boolean downloadFileLocal(String processId, boolean testfiles, String localDir, String... filenames) throws JSchException, SftpException {
		if (StringUtils.isEmpty(processId)) {
			return false;
		}
		processId = StringUtils.left(processId, 10);
		if (filenames == null || filenames.length < 1) {
			return true;
		}
		if(StringUtils.isEmpty(localDir)) {
			localDir = getTempFolder();
		}
		String path1 = "dev/" + StringUtils.mid(processId, 0, 4)
				+ "/inbound/" + processId;
		String path2 = "dev/"
				+ StringUtils.mid(processId, 0, 4)
				+ "/inbound/"
				+ processId.substring(4);
		if(testfiles) {
			path1 += "/testfiles/";
			path2 += "/testfiles/";
		}
		ChannelSftp sftp = JSFTP.getSftp(getHostname(), 22, getUsername(), getPassword());
		sftp.lcd(localDir);
		if(JSFTP.existing(sftp, path1)) {
			for(String filename:filenames) {
				sftp.get(path1 + filename, filename);
			}
		} else if(JSFTP.existing(sftp, path2)) {
			for(String filename:filenames) {
				sftp.get(path2 + filename, filename);
			}
		}
		JSFTP.disconnet(sftp);
		return true;
	}

}
