package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.system.SystemAuthBean;
import mysword.bean.system.SystemAuthMacBean;
import mysword.system.SystemAccess;
import mysword.system.SystemAuthentication;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccessAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private ArrayList<SystemAuthMacBean> macList = new ArrayList<SystemAuthMacBean>();

    public ArrayList<SystemAuthMacBean> getMacList() {
        return macList;
    }

    public void setMacList(ArrayList<SystemAuthMacBean> macList) {
        this.macList = macList;
    }

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public void deleteMac() throws SQLException, ClassNotFoundException {
        SystemAccess.deleteMacs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), macList.get(0).getMac());
    }

    public void updateMac() throws SQLException, ClassNotFoundException, UnknownHostException {
        SystemAccess.updateMacs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), macList.toArray(new SystemAuthMacBean[macList.size()]));
    }

    public void addMac() throws SQLException, ClassNotFoundException {
        SystemAccess.addMacs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), macList.toArray(new SystemAuthMacBean[macList.size()]));
    }

    public void listMacs() throws SQLException, ClassNotFoundException {
        macList = SystemAccess.getMacList();
    }

}
