package mysword.action.tools;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base64Action extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String inputContent;
	private String outputContent;
    private boolean utf8;

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public String getOutputContent() {
        return outputContent;
    }

    public void setOutputContent(String outputContent) {
        this.outputContent = outputContent;
    }

    public boolean isUtf8() {
        return utf8;
    }

    public void setUtf8(boolean utf8) {
        this.utf8 = utf8;
    }

    private File uploadFile;

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public void encode() throws IOException {
        byte[] inputBytes = new byte[0];
        if(StringUtils.isNotEmpty(inputContent)) {
            inputBytes = inputContent.getBytes(utf8?"utf-8":"ascii");
        } else if (uploadFile != null) {
            inputBytes = FileUtils.readFileToByteArray(uploadFile);
        }
        outputContent = new String(Base64.encodeBase64(inputBytes));
	}
	
	public void decode() throws IOException {
        byte[] inputBytes = new byte[0];
        if(StringUtils.isNotEmpty(inputContent)) {
            inputBytes = inputContent.getBytes(utf8?"utf-8":"ascii");
        } else if (uploadFile != null) {
            inputBytes = FileUtils.readFileToByteArray(uploadFile);
        }
        outputContent = new String(Base64.decodeBase64(inputBytes), utf8?"utf-8":"ascii");
	}
}
