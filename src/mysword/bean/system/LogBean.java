package mysword.bean.system;

public class LogBean {

	private String logTime;
	private String level;
	private String className;
	private String method;
	private String lineNo;
	private String message;
	private String lasteditby;
	private String lasteditdt;
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLasteditby() {
		return lasteditby;
	}
	public void setLasteditby(String lasteditby) {
		this.lasteditby = lasteditby;
	}
	public String getLasteditdt() {
		return lasteditdt;
	}
	public void setLasteditdt(String lasteditdt) {
		this.lasteditdt = lasteditdt;
	}
}
