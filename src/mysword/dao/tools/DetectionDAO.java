package mysword.dao.tools;

import mysword.system.conf.SystemConf;
import mysword.utils.EmailUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class DetectionDAO {
	
	public static String DEV = "development";
	public static String INT = "integration";
	public static String TEST = "test";
	public static String PROD = "production";
	
	public static String getReceiveAddress(String instance) {
		if(StringUtils.equals(DEV, instance)) {
			return SystemConf.getConfString("server.email.address.dev");
		} else if(StringUtils.equals(INT, instance)) {
			return SystemConf.getConfString("server.email.address.int");
		} else if(StringUtils.equals(TEST, instance)) {
			return SystemConf.getConfString("server.email.address.test");
		} else if(StringUtils.equals(PROD, instance)) {
			return SystemConf.getConfString("server.email.address.prod");
		} else {
			return "";
		}
	}

	public static String emailDetection(String instance, String subject, File... files) throws Exception {
		if(StringUtils.isEmpty(instance) || StringUtils.isEmpty(getReceiveAddress(instance))) {
			return "Error: no mail from address found!!!";
		}
		if(files == null || files.length < 1) {
			return "Error: no attachments!!!";
		}
		StringBuffer logs = new StringBuffer();
		for(File file:files) {
			EmailUtils.send(new String[]{getReceiveAddress(instance)}, SystemConf.DEFAULT_FROM, subject, "", file);
			logs.append("Send email '" + subject + "' with file '" + file.getName() + "' successfully.");
		}
		return logs.toString();
	}

	public static String emailDetection(String instance, String subject, List<File> files) throws Exception {
		StringBuffer result = new StringBuffer();
		if(StringUtils.isEmpty(instance) || StringUtils.isEmpty(getReceiveAddress(instance))) {
			return "Error: no mail from address found!";
		}
		if(files.size() > 0) {
			for(File file:files) {
				EmailUtils.send(new String[]{getReceiveAddress(instance)}, SystemConf.DEFAULT_FROM, subject, "", file);
				result.append("Send email '" + subject + "' with attachement '" + file.getName() + "' successfully.\n");
			}
		} else {
			result.append("There are no attachments that need to be sent to Server!");
		}
		return result.toString();
	}

	public static String emailDetectionStreamFile(String instance, String subject, InputStream... inputStreams) throws Exception {
		if(StringUtils.isEmpty(instance) || StringUtils.isEmpty(getReceiveAddress(instance))) {
			return "Error: no mail from address found!";
		}
		for(InputStream is:inputStreams) {
			EmailUtils.send(new String[]{getReceiveAddress(instance)}, SystemConf.DEFAULT_FROM, subject, "", is);
		}
		return "Send the email to server successfully!";
	}

	public static String emailDetectionStreamFile(String instance, String subject, List<InputStream> inputStreams) throws Exception {
		if(StringUtils.isEmpty(instance) || StringUtils.isEmpty(getReceiveAddress(instance))) {
			return "Error: no mail from address found!";
		}
		if(inputStreams.size() > 0) {
			for(InputStream is:inputStreams) {
				EmailUtils.send(new String[]{getReceiveAddress(instance)}, SystemConf.DEFAULT_FROM, subject, "", is);
			}
		} else {
			EmailUtils.send(new String[]{getReceiveAddress(instance)}, SystemConf.DEFAULT_FROM, subject, "");
		}
		return "Send the email to server successfully!";
	}
}
