package mysword.action.knowledgebase;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.common.FileBean;
import mysword.dao.knowledge.HowToDAO;

import java.io.InputStream;
import java.util.ArrayList;

public class HowToAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String filename;
	private InputStream fileStream;
	private ArrayList<FileBean> fileList = new ArrayList<FileBean>();
	

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public ArrayList<FileBean> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<FileBean> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void howToFileList() {
		fileList = HowToDAO.getHowToFileList();
	}

	public String downloadFile() throws Exception {
        fileStream = HowToDAO.getHowToFileIO(filename);
        return "download_file";
	}
}
