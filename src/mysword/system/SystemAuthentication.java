package mysword.system;

import mysword.bean.system.SystemAuthBean;
import mysword.bean.system.SystemAuthMacBean;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("unused")
public class SystemAuthentication {

	public static int addUsers(String requester, SystemAuthBean... sab) throws SQLException, ClassNotFoundException {
        if(sab == null || sab.length == 0) {
            return 0;
        }
        Object[][] params = new Object[sab.length][17];
        String[] users = new String[0];
        for(int i=0; i<sab.length; i++) {
            users = (String[])ArrayUtils.add(users, sab[i].getUsername());
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
            params[i][16] = sab[i].getUserIndex();
        }
        int result = batchUpdateDatabase(
                "insert into MYSWORD_AUTH(HOSTNAME,USERNAME,SIMS,SIMS_PASSWORD,REGION,EMAIL,EXTENSION,DEPARTMENT,SYSADMIN,KNOWLEDGE_SHARING_ADMIN,KNOWLEDGE_SHARING_USER,DEVELOPER,SUPPORT," +
                        "LASTEDITBY,LASTEDITDT,THEME,USERINDEX) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
		MyLogger.sysLogger.info(requester + " add user: " + StringUtils.join(users, ","));
        return result;
	}

    @SuppressWarnings("unchecked")
	public static void deleteUsers(String requester, String... username) throws SQLException, ClassNotFoundException {
        updateDatabase("delete from MYSWORD_AUTH with(rowlock) where USERNAME in ('" + StringUtils.join(username, "','") + "')");
		MyLogger.sysLogger.info(requester + " delete " + StringUtils.join(username, ","));
	}

    public static SystemAuthBean getUser(String username) throws SQLException, ClassNotFoundException {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        ArrayList<SystemAuthBean> userList = queryDatabase("select * from MYSWORD_AUTH(nolock) where USERNAME=? order by USERNAME asc", username);
        if(userList == null || userList.size()==0) {
            return null;
        } else {
            userList.get(0).setMac(SystemAccess.getMacList(username));
            return userList.get(0);
        }
    }

    public static SystemAuthBean getUserByMac(String mac) throws SQLException, ClassNotFoundException {
        if(StringUtils.isEmpty(mac)) {
            return null;
        }
        ArrayList<SystemAuthBean> userList = queryDatabase("select t1.* from MYSWORD_AUTH(nolock) t1 left join MYSWORD_AUTH_MAC(nolock) t2 on t1.USERNAME=t2.USERNAME where t2.MAC=? order by t1.USERNAME asc", mac);
        if(userList == null || userList.size()==0) {
            return null;
        } else {
            userList.get(0).setMac(SystemAccess.getMacList(userList.get(0).getUsername()));
            return userList.get(0);
        }
    }

    public static ArrayList<SystemAuthBean> getUsers() throws SQLException, ClassNotFoundException {
        ArrayList<SystemAuthBean> userList = queryDatabase("select * from MYSWORD_AUTH(nolock) order by USERNAME asc");
        if(userList == null) {
            return null;
        }
        for (SystemAuthBean user : userList) {
            user.setMac(SystemAccess.getMacList(user.getUsername()));
        }
        return userList;
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
        Object[][] params = new Object[sab.length][18];
        StringBuilder users = new StringBuilder();
        int j=0;
        for(int i=0; i<sab.length; i++) {
            users.append(sab[i].getUsername());
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
            params[i][15] = sab[i].getUserIndex();
            params[i][16] = sab[i].getHostname();
            params[i][17] = sab[i].getUsername();
        }
//        Connection conn = SystemDatabase.getConnection("SYSTEM");
        int result = batchUpdateDatabase("update MYSWORD_AUTH with(rowlock) set USERNAME=?,SIMS=?,SIMS_PASSWORD=?,REGION=?,EMAIL=?,EXTENSION=?,DEPARTMENT=?,SYSADMIN=?,KNOWLEDGE_SHARING_ADMIN=?," +
            "KNOWLEDGE_SHARING_USER=?,DEVELOPER=?,SUPPORT=?,LASTEDITBY=?,LASTEDITDT=?,THEME=?,USERINDEX=?,HOSTNAME=? where USERNAME=?", params);
//        SystemDatabase.update(conn, "delete from MYSWORD_AUTH_MAC with(rowlock) where MAC in ('?')", StringUtils.join(macs, "','"));
//        SystemDatabase.batchUpdateDatabase(conn, "insert into MYSWORD_AUTH_MAC(MAC,USERNAME) values(?,?) ", macParams);
//        int result = batchUpdateDatabase("update MYSWORD_AUTH with(rowlock) set USERNAME=?, SIMS=?, SIMS_PASSWORD=?, REGION=?, EMAIL=?, EXTENSION=?, DEPARTMENT=?, SYSADMIN=?, KNOWLEDGE_SHARING_ADMIN=?, " +
//                "KNOWLEDGE_SHARING_USER=?, DEVELOPER=?, SUPPORT=?, LASTEDITBY=?, LASTEDITDT=?, THEME=?,USERINDEX=? where USERNAME=?", params);
		MyLogger.sysLogger.info(requester + " update users: " + users.toString());
        return result;
	}


}
