package mysword.action.test;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TestAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    private ArrayList<String> result = new ArrayList<String>();

	@Override
	public String execute() throws Exception {
        result.add("1");
        result.add("2");
        result.add("3");
		return SUCCESS;
	}
}
