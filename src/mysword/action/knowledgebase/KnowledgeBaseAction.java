package mysword.action.knowledgebase;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.knowledgebase.ComsysBean;
import mysword.bean.knowledgebase.ServerBean;
import mysword.dao.knowledgebase.ComsysDAO;
import mysword.dao.knowledgebase.ServerDAO;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;

public class KnowledgeBaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String environment;
    private ArrayList<ComsysBean> comsysList = new ArrayList<ComsysBean>();
    private ArrayList<ServerBean> serverList = new ArrayList<ServerBean>();

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public ArrayList<ComsysBean> getComsysList() {
        return comsysList;
    }

    public void setComsysList(ArrayList<ComsysBean> comsysList) {
        this.comsysList = comsysList;
    }

    public ArrayList<ServerBean> getServerList() {
        return serverList;
    }

    public void setServerList(ArrayList<ServerBean> serverList) {
        this.serverList = serverList;
    }

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public String comsys() {
        return "success_comsys";
    }

    public void listComsys() throws SQLException, ClassNotFoundException {
        if(StringUtils.isNotEmpty(environment)) {
            comsysList = ComsysDAO.getComsysByEnv(environment);
        } else {
            comsysList = ComsysDAO.getComsysList();
        }
    }

    public String schenker() { return "success_schenker"; }

    public String ssc() { return "success_ssc"; }

    public String edi() { return "success_edi"; }

    public String edifact() { return "success_edifact"; }

    public String flatfile() { return "success_flatfile"; }

    public String regularExpression() { return "success_regularExpression"; }

    public String server() { return "success_server"; }

    public void listServer() throws SQLException, ClassNotFoundException {
        if(StringUtils.isNotEmpty(environment)) {
            serverList = ServerDAO.getServerByEnv(environment);
        } else {
            serverList = ServerDAO.getServerList();
        }
    }

    public String x12() { return "success_x12"; }

    public String xml() { return "success_xml"; }

    public String howTo() { return "success_howTo"; }

}
