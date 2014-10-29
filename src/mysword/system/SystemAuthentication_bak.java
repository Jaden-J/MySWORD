package mysword.system;

import mysword.bean.system.SystemAuthBean;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("unused")
public class SystemAuthentication_bak {

	public static int addUsers(String requester, SystemAuthBean... sab) throws SQLException, ClassNotFoundException {
        if(sab == null || sab.length == 0) {
            return 0;
        }
        Object[][] params = new Object[sab.length][16];
        String[] users = new String[0];
        for(int i=0; i<sab.length; i++) {
            String userDetail = sab[i].getUsername() + "[" + "[" + sab[i].getHostname() + "]";
            users = (String[])ArrayUtils.add(users, userDetail);
            params[i][0] = sab[i].getHostname();
            params[i][1] = sab[i].getUsername();
            params[i][2] = sab[i].getSims();
            params[i][3] = sab[i].getSims_password();
            params[i][4] = sab[i].getRegion();
            params[i][5] = sab[i].getEmail();
            params[i][6] = sab[i].getExtension();
            params[i][7] = sab[i].getDepartment();
            params[i][8] = sab[i].isSysadmin()?1:0;
            params[i][9] = sab[i].isKnowledge_sharing_admin()?1:0;
            params[i][10] = sab[i].isKnowledge_sharing_user()?1:0;
            params[i][11] = sab[i].isDeveloper()?1:0;
            params[i][12] = sab[i].isSupport()?1:0;
            sab[i].setLasteditby(requester);
            params[i][13] = sab[i].getLasteditby();
            sab[i].setLasteditdt(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            params[i][14] = sab[i].getLasteditdt();
            params[i][15] = sab[i].getTheme();
        }
        int result = batchUpdateDatabase(
                "insert into MYSWORD_AUTH(HOSTNAME,USERNAME,SIMS,SIMS_PASSWORD,REGION,EMAIL,EXTENSION,DEPARTMENT,SYSADMIN,KNOWLEDGE_SHARING_ADMIN,KNOWLEDGE_SHARING_USER,DEVELOPER,SUPPORT," +
                        "LASTEDITBY,LASTEDITDT,THEME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
		MyLogger.sysLogger.info(requester + " add user[HOSTNAME]: " + StringUtils.join(users, ","));
        return result;
	}

    @SuppressWarnings("unchecked")
	public static void deleteUsers(String requester, String... hostname) throws SQLException, ClassNotFoundException {
        updateDatabase("delete from MYSWORD_AUTH with(rowlock) where HOSTNAME in ('" + StringUtils.join(hostname, "','") + "')");
		MyLogger.sysLogger.info(requester + " delete " + StringUtils.join(hostname, ","));
	}

    public static SystemAuthBean getUser(String hostname) throws SQLException, ClassNotFoundException {
        if(StringUtils.isEmpty(hostname)) {
            return null;
        }
        ArrayList<SystemAuthBean> userList = queryDatabase("select * from MYSWORD_AUTH(nolock) where HOSTNAME=? order by USERNAME asc", hostname);
        if(userList == null || userList.size()==0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    public static ArrayList<SystemAuthBean> getUsers() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH(nolock) order by USERNAME asc");
    }

    public static ArrayList<SystemAuthBean> getSysAdmins() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH(nolock) where SYSADMIN=1 order by USERNAME asc");
    }

    public static ArrayList<SystemAuthBean> getKnowledgeSharingAdmins() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH(nolock) where KNOWLEDGE_SHARING_ADMIN=1 order by USERNAME asc");
    }

    public static ArrayList<SystemAuthBean> getKnowledgeSharingUsers() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH(nolock) where KNOWLEDGE_SHARING_USER=1 order by USERNAME asc");
    }

    public static ArrayList<SystemAuthBean> getKnowledgeSharingMembers() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH(nolock) where KNOWLEDGE_SHARING_USER=1 or KNOWLEDGE_SHARING_ADMIN=1 order by USERNAME asc");
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<SystemAuthBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<SystemAuthBean>)SystemDatabase.queryDatabase("SYSTEM", sql, SystemAuthBean.class, params);
    }

    private static int updateDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.updateDatabase("SYSTEM", sql, params);
    }

    private static int batchUpdateDatabase(String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.batchUpdateDatabase("SYSTEM", sql, params);
    }

    @SuppressWarnings("unchecked")
	public static int updateUsers(String requester, SystemAuthBean... sab) throws SQLException, ClassNotFoundException {
        if(sab == null || sab.length == 0) {
            return 0;
        }
        Object[][] params = new Object[sab.length][16];
        StringBuilder users = new StringBuilder();
        for(int i=0; i<sab.length; i++) {
            users.append(sab[i].getUsername());
            String hsotname_temp = "[" + sab[i].getHostname() + "] ";
            users.append(hsotname_temp);
            params[i][0] = sab[i].getUsername();
            params[i][1] = sab[i].getSims();
            params[i][2] = sab[i].getSims_password();
            params[i][3] = sab[i].getRegion();
            params[i][4] = sab[i].getEmail();
            params[i][5] = sab[i].getExtension();
            params[i][6] = sab[i].getDepartment();
            params[i][7] = sab[i].isSysadmin()?1:0;
            params[i][8] = sab[i].isKnowledge_sharing_admin()?1:0;
            params[i][9] = sab[i].isKnowledge_sharing_user()?1:0;
            params[i][10] = sab[i].isDeveloper()?1:0;
            params[i][11] = sab[i].isSupport()?1:0;
            sab[i].setLasteditby(requester);
            params[i][12] = sab[i].getLasteditby();
            sab[i].setLasteditdt(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            params[i][13] = sab[i].getLasteditdt();
            params[i][14] = sab[i].getTheme();
            params[i][15] = sab[i].getHostname();
        }
        int result = batchUpdateDatabase("update MYSWORD_AUTH with(rowlock) set USERNAME=?, SIMS=?, SIMS_PASSWORD=?, REGION=?, EMAIL=?, EXTENSION=?, DEPARTMENT=?, SYSADMIN=?, KNOWLEDGE_SHARING_ADMIN=?, " +
                "KNOWLEDGE_SHARING_USER=?, DEVELOPER=?, SUPPORT=?, LASTEDITBY=?, LASTEDITDT=?, THEME=? where HOSTNAME=?", params);
		MyLogger.sysLogger.info(requester + " update user[HOSTNAME]: " + users.toString());
        return result;
	}


}
