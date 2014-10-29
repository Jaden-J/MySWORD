package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.utils.ServletActionContextUtils;

public class SystemAction extends ActionSupport {

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

    public void error() {}

    public String logout() {
        ServletActionContextUtils.removeAllRequestAttributes();
        ServletActionContextUtils.removeAllSeesionAttributes();
        return "logout";
    }
}
