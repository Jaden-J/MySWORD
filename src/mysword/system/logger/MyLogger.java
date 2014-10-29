package mysword.system.logger;

import mysword.bean.system.LogBean;
import mysword.bean.system.ResultCount;
import mysword.system.database.SystemDatabase;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyLogger {
	public static Logger sysLogger = Logger.getLogger("mysword");
	
	public static String getErrorString(Throwable e) {
		if(e != null && e.getCause() != null) {
			return e.getMessage();
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public static int getLogCount(String startTime, String endTime, String level, String message, String username) throws Exception {
		String sql = "select * as COUNT from MYSWORD_LOG";
		StringBuffer condition = new StringBuffer("");
		if(StringUtils.isNotEmpty(startTime)) {
			condition.append(" and LOGTIME >= '" + startTime + "'");
		}
		if(StringUtils.isNotEmpty(endTime)) {
			condition.append(" and LOGTIME <= '" + endTime + "'");
		}
		if(StringUtils.isNotEmpty(level)) {
			condition.append(" and LEVEL = '" + level + "'");
		}
		if(StringUtils.isNotEmpty(message)) {
			condition.append(" and MESSAGE like '%" + message + "%'");
		}
		if(StringUtils.isNotEmpty(username)) {
			condition.append(" and MESSAGE like '" + username + "%'");
		}
		if(StringUtils.isNotEmpty(condition)) {
			sql = sql + " where" + condition.toString().replaceFirst(" and", "");
		}
		ArrayList<LogBean> result = queryDatabase(sql);
		if(result != null && result.size() > 0) {
			return result.size();
		} else {
			return 0;
		}
	}

    public static ArrayList<LogBean> queryDatabase(String sql, String... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<LogBean>)SystemDatabase.queryDatabase("LOG", sql, LogBean.class, params);
    }
	
	@SuppressWarnings("unchecked")
	public static ArrayList<LogBean> getLogs(String startTime, String endTime, String level, String message, String sims, int start, int end) throws Exception {
		String sql = "select * from MYSWORD_LOG";
		StringBuilder condition = new StringBuilder();
		if(StringUtils.isNotEmpty(startTime)) {
			condition.append(" and LOGTIME >= '" + startTime + "'");
		}
		if(StringUtils.isNotEmpty(endTime)) {
			condition.append(" and LOGTIME <= '" + endTime + "'");
		}
		if(StringUtils.isNotEmpty(level)) {
			condition.append(" and LEVEL = '" + level + "'");
		}
		if(StringUtils.isNotEmpty(message)) {
			condition.append(" and MESSAGE like '%" + message + "%'");
		}
		if(StringUtils.isNotEmpty(sims)) {
			condition.append(" and MESSAGE like '" + sims + "%'");
		}
		if(StringUtils.isNotEmpty(condition)) {
			sql = sql + " where" + condition.toString().replaceFirst(" and", "");
		}
		sql += " order by LOGTIME desc";
		if (start >= -1 && end >= -1) {
			sql += " limit " + start + "," + end;
		}
        return queryDatabase(sql);
	}
}
