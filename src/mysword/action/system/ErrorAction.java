package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;

public class ErrorAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
