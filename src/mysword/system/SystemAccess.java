package mysword.system;

import mysword.bean.system.SystemAuthMacBean;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2014/8/23.
 */
public class SystemAccess {

    public static ArrayList<SystemAuthMacBean> getMacList() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH_MAC(nolock) order by USERNAME asc");
    }

    public static ArrayList<SystemAuthMacBean> getMacList(String username) throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_AUTH_MAC(nolock) where USERNAME=? order by MAC asc", username);
    }

    public static int addMacs(String username, SystemAuthMacBean... sambList) throws SQLException, ClassNotFoundException {
        if(sambList==null||sambList.length==0) {
            return 0;
        }
        Object[][] params = new Object[sambList.length][5];
        String[] macs=new String[0];
        for(int i=0; i<sambList.length; i++) {
            macs = (String[]) ArrayUtils.add(macs, sambList[i].getMac());
            params[i][0] = sambList[i].getMac();
            params[i][1] = sambList[i].getUsername();
            params[i][2] = sambList[i].getDescription();
            sambList[i].setLasteditby(username);
            params[i][3] = sambList[i].getLasteditby();
            sambList[i].setLasteditdt(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            params[i][4] = sambList[i].getLasteditdt();
        }
        MyLogger.sysLogger.info(username+" add macs: "+ StringUtils.join(macs,","));
        return batchUpdateDatabase("insert into MYSWORD_AUTH_MAC(MAC,USERNAME,DESCRIPTION,LASTEDITBY,LASTEDITDT) values(?,?,?,?,?)", params);
    }

    public static int updateMacs(String username, SystemAuthMacBean... sambList) throws SQLException, ClassNotFoundException {
        if(sambList==null||sambList.length==0) {
            return 0;
        }
        Object[][] params = new Object[sambList.length][6];
        String[] macs=new String[0];
        for(int i=0; i<sambList.length; i++) {
            macs = (String[]) ArrayUtils.add(macs, sambList[i].getMac());
            params[i][0] = sambList[i].getMac();
            params[i][1] = sambList[i].getUsername();
            params[i][2] = sambList[i].getDescription();
            sambList[i].setLasteditby(username);
            params[i][3] = sambList[i].getLasteditby();
            sambList[i].setLasteditdt(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            params[i][4] = sambList[i].getLasteditdt();
            params[i][5] = sambList[i].getMac();
        }
        MyLogger.sysLogger.info(username+" update macs: "+ StringUtils.join(macs,","));
        return batchUpdateDatabase("update MYSWORD_AUTH_MAC with(rowlock) set MAC=?,USERNAME=?,DESCRIPTION=?,LASTEDITBY=?,LASTEDITDT=? where MAC=?", params);
    }

    public static int deleteMacs(String username, String... macs) throws SQLException, ClassNotFoundException {
        if(macs==null||macs.length==0) {
            return 0;
        }
        Object[][] params = new Object[macs.length][1];
        for(int i=0; i<macs.length; i++) {
            params[i][0] = macs[i];
        }
        MyLogger.sysLogger.info(username+" delete macs: "+ StringUtils.join(macs,","));
        return batchUpdateDatabase("delete from MYSWORD_AUTH_MAC with(rowlock) where MAC=?", params);
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<SystemAuthMacBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<SystemAuthMacBean>) SystemDatabase.queryDatabase("SYSTEM", sql, SystemAuthMacBean.class, params);
    }

    private static int batchUpdateDatabase(String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.batchUpdateDatabase("SYSTEM", sql, params);
    }
}
