package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.system.SystemAuthBean;
import mysword.system.SystemAuthentication;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private ArrayList<SystemAuthBean> userList = new ArrayList<SystemAuthBean>();

    public ArrayList<SystemAuthBean> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<SystemAuthBean> userList) {
        this.userList = userList;
    }

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public String showProfileList() { return "show_list"; }

    public String showProfile() {
        return "show_one";
    }

    public void myProfile() throws SQLException, ClassNotFoundException {
        userList.add(0, SystemAuthentication.getUser(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))));
    }

    public void deleteProfile() throws SQLException, ClassNotFoundException {
        SystemAuthentication.deleteUsers(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), userList.get(0).getUsername());
    }

    public void updateProfile() throws SQLException, ClassNotFoundException, UnknownHostException {
        SystemAuthBean sab = userList.get(0);
        if((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_sysadmin")) {
            SystemAuthentication.updateUsers(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), sab);
        } else {
            if(StringUtils.equals(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), sab.getUsername())) {
                sab.setSysadmin((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_sysadmin"));
                sab.setKnowledge_sharing_admin((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin"));
                sab.setKnowledge_sharing_user((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_user"));
                sab.setDeveloper((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_developer"));
                sab.setSupport((Boolean)ServletActionContextUtils.getSessionAttribute("mysword_support"));
                SystemAuthentication.updateUsers(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), sab);
            } else {
                throw new RuntimeException("Only system admin can update other profiles!");
            }
        }
        ServletActionContextUtils.removeAllRequestAttributes();
        ServletActionContextUtils.removeAllSeesionAttributes();
    }

    public void addProfile() throws SQLException, ClassNotFoundException {
        SystemAuthentication.addUsers(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), userList.get(0));
    }

    public void listProfiles() throws SQLException, ClassNotFoundException {
        userList = SystemAuthentication.getUsers();
    }

}
