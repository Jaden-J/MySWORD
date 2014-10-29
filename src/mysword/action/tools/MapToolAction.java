package mysword.action.tools;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.common.FileBean;
import mysword.bean.system.SystemAuthBean;
import mysword.dao.tools.ComsysDAO;
import mysword.dao.tools.DetectionDAO;
import mysword.dao.tools.MapDAO;
import mysword.dao.tools.MasstestDAO;
import mysword.system.SystemAuthentication;
import mysword.utils.MapUtils;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class MapToolAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
    private String mapName;
    private String description;
    private String instance;
    private String versionIndex;
    private String logs;
    private ArrayList<String> archiveList = new ArrayList<String>();
    private HashMap<String, ArrayList<SVNLogEntry>> revisions = new HashMap<String, ArrayList<SVNLogEntry>>();

    public ArrayList<String> getArchiveList() {
        return archiveList;
    }

    public void setArchiveList(ArrayList<String> archiveList) {
        this.archiveList = archiveList;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getVersionIndex() {
        return versionIndex;
    }

    public void setVersionIndex(String versionIndex) {
        this.versionIndex = versionIndex;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public HashMap<String, ArrayList<SVNLogEntry>> getRevisions() {
        return revisions;
    }

    public void setRevisions(HashMap<String, ArrayList<SVNLogEntry>> revisions) {
        this.revisions = revisions;
    }

    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public void listRevisions() throws SVNException {
        revisions = MapDAO.getMapRevisions(mapName);
        logs="List revisions for DEV/INT/TEST/PROD successfully.";
    }

    public void checkInMap() throws SQLException, ClassNotFoundException, InterruptedException, JSchException, IOException {
        SystemAuthBean sab = SystemAuthentication.getUser(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
        if(StringUtils.isNotEmpty(sab.getUserIndex())) {
            logs = MapDAO.checkInMap(sab.getUserIndex(), mapName, description);
        } else {
            logs = "You haven't configed your user index! Please config it: System-->MyProfile-->User Index";
        }
    }

    public void rolloutMap() throws SQLException, ClassNotFoundException, InterruptedException, JSchException, IOException {
        SystemAuthBean sab = SystemAuthentication.getUser(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
        if(StringUtils.isNotEmpty(sab.getUserIndex())) {
            logs = MapDAO.rolloutMap(sab.getUserIndex(), instance, mapName, versionIndex);
        } else {
            logs = "You haven't configed your user index! Please config it: System-->MyProfile-->User Index";
        }
    }

    public void runGetmap() throws InterruptedException, JSchException, IOException {
        logs = MapDAO.getMap(mapName, instance, String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
    }

    public void listArchive() throws JSchException, SftpException {
        archiveList = MapDAO.getArchiveList(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
    }
}
