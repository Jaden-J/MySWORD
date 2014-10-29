package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.system.SystemConfBean;
import mysword.system.conf.SystemConf;
import mysword.utils.ServletActionContextUtils;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConfigAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private ArrayList<SystemConfBean> configList = new ArrayList<SystemConfBean>();

    public ArrayList<SystemConfBean> getConfigList() {
        return configList;
    }

    public void setConfigList(ArrayList<SystemConfBean> configList) {
        this.configList = configList;
    }

    @Override
	public String execute() throws Exception {
        return SUCCESS;
	}

    public void listConfigs() throws SQLException, ClassNotFoundException {
        configList = SystemConf.listConfigDetail();
    }

    public void updateConfig() throws SQLException, ClassNotFoundException, UnknownHostException {
        SystemConf.updateConfig(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), configList.get(0));
    }

    public void addConfig() throws SQLException, ClassNotFoundException, UnknownHostException {
        SystemConf.addConfig(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), configList.get(0));
    }

    public void deleteConfig() throws SQLException, ClassNotFoundException, UnknownHostException {
        SystemConf.deleteConfig(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), configList.get(0).getConfig_name());
    }
}
