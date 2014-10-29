package mysword.action.tools;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.common.FileBean;
import mysword.dao.tools.MasstestDAO;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MasstestAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String processId;
	private String actionType;
	private String folder;
	private String masstessFilename;
	private String filename;
	private String system;
	private String inputContent;
	private String ftpLogs;
	private String actionLogs;
	private boolean backup;
	private File uploadFile;
	private String uploadFileFileName;
	private ArrayList<FileBean> ftpFileList = new ArrayList<FileBean>();
	private HashMap<String, Object> result;
	
	@Override
	public String execute() throws Exception {
		if ("Create".equals(actionType)) {
			ftpLogs = MasstestDAO.createMasstess(processId);
			ftpLogs += uploadInboundFile(backup);
		} else if ("Upload".equals(actionType)) {
			if(StringUtils.isEmpty(inputContent) && uploadFile == null) {
				ftpLogs = "Sorry, please specify the upload file or input the file content!";
			} else {
				if(StringUtils.isNotEmpty(processId)) {
					ftpLogs = uploadInboundFile(backup);
				} else if (StringUtils.isNotEmpty(folder)){
					ftpLogs = uploadFile();
				}
			}
		} else if ("DemoTest".equals(actionType)) {
			ftpLogs = uploadInboundFile(true);
			String demoTestFilename = "";
			if(StringUtils.isNotEmpty(filename)) {
				demoTestFilename = filename;
			} else if(StringUtils.isNotEmpty(uploadFileFileName)) {
				demoTestFilename = uploadFileFileName;
			} else if(StringUtils.isNotEmpty(masstessFilename)) {
				demoTestFilename = masstessFilename;
			}
			if(StringUtils.isNotEmpty(demoTestFilename)){
				actionLogs = MasstestDAO.demoTest(processId, demoTestFilename);
			} else {
				actionLogs = "Sorry, please specify the demo test filename!";
			}
		}
		ftpFileList = MasstestDAO.getMasstessFileList(processId, "inbound", true);
		FileBean fb = new FileBean();
		fb.setFilename("");
		ftpFileList.add(0, fb);
		return "input";
	}
	
	private String uploadFile() throws Exception {
		String resultString = "";
		if (StringUtils.isNotEmpty(inputContent)) {
			String temp_content = "";
			if ("unix".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "\n");
			} else if ("mac".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "\r");
			} else if ("no".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "");
			} else {
				temp_content = inputContent;
			}
			resultString = MasstestDAO.uploadFile(folder, filename, temp_content);
		} else if (uploadFile != null) {
			resultString = MasstestDAO.uploadFile(folder, uploadFileFileName, uploadFile);
		}
		return resultString;
	}
	
	private String uploadInboundFile(boolean bak) throws Exception {
		String resultString = "";
		if (StringUtils.isNotEmpty(inputContent)) {
			String temp_content = "";
			if ("unix".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "\n");
			} else if ("mac".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "\r");
			} else if ("no".equals(system)) {
				temp_content = inputContent.replaceAll("\r\n", "");
			} else {
				temp_content = inputContent;
			}
			resultString = MasstestDAO.uploadInboundFile(processId, filename, temp_content, bak);
		} else if (uploadFile != null) {
			resultString = MasstestDAO.uploadInboundFile(processId, uploadFileFileName, uploadFile, bak);
		}
		return resultString;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getMasstessFilename() {
		return masstessFilename;
	}

	public void setMasstessFilename(String masstessFilename) {
		this.masstessFilename = masstessFilename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getInputContent() {
		return inputContent;
	}

	public void setInputContent(String inputContent) {
		this.inputContent = inputContent;
	}

	public String getFtpLogs() {
		return ftpLogs;
	}

	public void setFtpLogs(String ftpLogs) {
		this.ftpLogs = ftpLogs;
	}

	public String getActionLogs() {
		return actionLogs;
	}

	public void setActionLogs(String actionLogs) {
		this.actionLogs = actionLogs;
	}

	public boolean isBackup() {
		return backup;
	}

	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public ArrayList<FileBean> getFtpFileList() {
		return ftpFileList;
	}

	public void setFtpFileList(ArrayList<FileBean> ftpFileList) {
		this.ftpFileList = ftpFileList;
	}

	public HashMap<String, Object> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Object> result) {
		this.result = result;
	}
	
}
