package mysword.action.tools;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.common.FileBean;
import mysword.dao.tools.ComsysDAO;
import mysword.dao.tools.DetectionDAO;
import mysword.dao.tools.MasstestDAO;
import mysword.utils.MapUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class TestToolAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String keyId;
	private String instance;
	private String uploadPath;
	private String encoding;
	private String subject;
	private String filename;
	private String content;
	private String newline;
	private File uploadFile;
	private String resultString = "";
    private String srcString;
    private String dstString;
	private ArrayList<FileBean> ftpFileList = new ArrayList<FileBean>();
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void emailDetection() throws Exception {
		LinkedList<File> files = new LinkedList<File>();
		if(StringUtils.isNotEmpty(filename)) {
			uploadFile(true);
			FileBean fb = new FileBean();
			fb.setFilename(filename);
			ftpFileList.add(fb);
		}
		if(ftpFileList.size() > 0) {
			String[] filenames = new String[0];
			for(FileBean fbean:ftpFileList) {
				filenames = ArrayUtils.add(filenames, fbean.getFilename());
			}
			String localDir = MasstestDAO.getTempFolder() + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH_mm_ss_SSS");
			FileUtils.forceMkdir(new File(localDir));
			MasstestDAO.downloadFileLocal(keyId, true, localDir, filenames);
			files = (LinkedList<File>)FileUtils.listFiles(new File(localDir), null, false);
		}
		resultString = DetectionDAO.emailDetection(DetectionDAO.DEV, StringUtils.isEmpty(subject)?keyId:subject, files);
	}

	public void createFolder() throws Exception {
		resultString = MasstestDAO.createMasstess(StringUtils.left(keyId,10));
	}

	public void receiveDemo() throws Exception {
		if(StringUtils.isNotEmpty(filename)) {
			uploadFile(true);
			FileBean fb = new FileBean();
			fb.setFilename(filename);
			ftpFileList.add(fb);
		}
		String[] filenames = new String[0];
		for(FileBean file_temp:ftpFileList) {
			filenames = ArrayUtils.add(filenames, file_temp.getFilename());
		}
		resultString = MasstestDAO.demoTest(StringUtils.substring(keyId, 0, 10), filenames);
	}
	
	public void uploadFile() throws Exception {
		uploadFile(true);
	}
	
	public void uploadFile(boolean deleteAfterUpload) throws Exception {
		if(StringUtils.isNotEmpty(filename)) {
			File temp_file = new File(filename + DateFormatUtils.format(new Date(), ".yyyyMMddHHmmssSSS"));
			if(uploadFile != null) {
				temp_file = uploadFile;
			} else {
				content = formatContent();
				if("ANSI/ASCII".equals(encoding)) {
                    FileUtils.writeStringToFile(temp_file, content, "ASCII", false);
                }else if("UTF-8".equals(encoding)) {
                    FileUtils.writeByteArrayToFile(temp_file, new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
                    FileUtils.writeStringToFile(temp_file, content, "UTF-8", true);
                }else if("UTF-8 NO BOM".equals(encoding)) {
                    FileUtils.writeStringToFile(temp_file, content, "UTF-8", false);
                }else if("Unicode".equals(encoding)) {
                    FileUtils.writeStringToFile(temp_file, content, "unicode", false);
				}
			}
			if(StringUtils.isNotEmpty(uploadPath)) {
				resultString += MasstestDAO.uploadFile(uploadPath, filename, new FileInputStream(temp_file));
			} else {
				resultString += MasstestDAO.uploadInboundFile(StringUtils.left(keyId, 10), filename, new FileInputStream(temp_file), true);
			}
			if(deleteAfterUpload) {
				temp_file.delete();
			}
		} else {
			resultString += "There is no file needs to be uploaded.";
		}
	}
	
	public void runAgreement() throws Exception {
		String[] comsys = StringUtils.split(instance, "-");
		if(StringUtils.equals(comsys[0],"dev")) {
			String[] filenames = new String[0];
			if(StringUtils.isNotEmpty(filename)) {
				uploadFile(true);
				FileBean fb = new FileBean();
				fb.setFilename(filename);
				ftpFileList.add(fb);
			}
			for(FileBean fbean:ftpFileList) {
				filenames = ArrayUtils.add(filenames, fbean.getFilename());
			}
			resultString += MasstestDAO.copyTestFileToInbound(StringUtils.substring(keyId, 0, 10), filenames);
			resultString += MapUtils.logMapToString((ListOrderedMap) ComsysDAO.runAgreement(keyId, comsys[0], NumberUtils.toInt(comsys[2].toLowerCase(), 1)).get("logs"), "shell");
		} else {
			resultString = "Sorry, you do not have the right to run the agreement except in 'Development'.";
		}
	}

	public void reloadAgreement() throws Exception {
		String[] comsys = StringUtils.split(instance, "-");
		if(StringUtils.equals(comsys[0],"dev")) {
			resultString += MapUtils.logMapToString((ListOrderedMap) ComsysDAO.reloadAgreement(keyId, comsys[0], String.valueOf(ServletActionContext.getRequest().getSession().getAttribute("mysword_username")), NumberUtils.toInt(comsys[2].toLowerCase(), 1)).get("logs"), "shell");
		} else {
			resultString = "Sorry, you do not have the right to run the agreement except in 'Development'.";
		}
	}
	public void ftpFileList() throws Exception {
        ftpFileList = MasstestDAO.getMasstessFileList(StringUtils.left(keyId, 10), "inbound", true);
        resultString = "List ftp files succefully.";
	}

    public void replaceString() {
        srcString = StringUtils.replace(srcString, "\\n", "\n");
        dstString = dstString.replace("\\n", "\n");
        content = StringUtils.replace(content, srcString, dstString);
    }
	
	private String formatContent() {
		if(StringUtils.equals("CR", newline)) {
			return StringUtils.replace(StringUtils.replace(content, "\r", ""), "\n", "\r");
		} else if(StringUtils.equals("LF", newline)) {
			return StringUtils.replace(StringUtils.replace(content, "\r", ""), "\n", "\n");
		} else if(StringUtils.equals("No", newline)) {
			return StringUtils.replace(StringUtils.replace(content, "\r", ""), "\n", "");
		} else if(StringUtils.equals("CRLF", newline)){
			return StringUtils.replace(StringUtils.replace(content, "\r", ""), "\n", "\r\n");
		} else {
			return content;
		}
	}

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewline() {
        return newline;
    }

    public void setNewline(String newline) {
        this.newline = newline;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getSrcString() {
        return srcString;
    }

    public void setSrcString(String srcString) {
        this.srcString = srcString;
    }

    public String getDstString() {
        return dstString;
    }

    public void setDstString(String dstString) {
        this.dstString = dstString;
    }

    public ArrayList<FileBean> getFtpFileList() {
        return ftpFileList;
    }

    public void setFtpFileList(ArrayList<FileBean> ftpFileList) {
        this.ftpFileList = ftpFileList;
    }
}
