package mysword.bean.common;

import java.util.Calendar;

public class FileBean {

	private String filename;
	private long fileSize;
	private Calendar fileTimestamp;
	private String fileGroup;
	private String fileType;
	private String username;
	private boolean isDirectory;
	private boolean isFile;
	private boolean isUnknown;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Calendar getFileTimestamp() {
		return fileTimestamp;
	}
	public void setFileTimestamp(Calendar fileTimestamp) {
		this.fileTimestamp = fileTimestamp;
	}
	public String getFileGroup() {
		return fileGroup;
	}
	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public boolean isUnknown() {
		return isUnknown;
	}
	public void setUnknown(boolean isUnknown) {
		this.isUnknown = isUnknown;
	}
}
