package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.system.SystemDatabaseBean;
import mysword.system.database.SystemDatabase;
import mysword.utils.ServletActionContextUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class DatabaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private ArrayList<SystemDatabaseBean> databaseList = new ArrayList<SystemDatabaseBean>();

    public ArrayList<SystemDatabaseBean> getDatabaseList() {
        return databaseList;
    }

    public void setDatabaseList(ArrayList<SystemDatabaseBean> databaseList) {
        this.databaseList = databaseList;
    }

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public void deleteDatabase() throws IOException {
        SystemDatabase.deleteDatabaseConfigs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), databaseList.get(0).getDatabaseName());
    }

    public void updateDatabase() throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SystemDatabase.updateDatabaseConfigs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), databaseList.toArray(new SystemDatabaseBean[databaseList.size()]));
    }

    public void addDatabase() throws IOException {
        SystemDatabase.addDatabaseConfigs(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), databaseList.toArray(new SystemDatabaseBean[databaseList.size()]));
    }

    public void listDatabases() {
        databaseList = SystemDatabase.getDatabaseConfigs();
    }

}
