package mysword.dao.knowledgebase;

import mysword.bean.knowledgebase.ServerBean;
import mysword.system.database.SystemDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hyao on 7/25/2014.
 */
public class ServerDAO {
    public static ArrayList<ServerBean> getServerList() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_SERVER(nolock) order by ENVIRONMENT,TITLE asc");
    }

    public static ArrayList<ServerBean> getServerByEnv(String environment) throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_SERVER(nolock) where ENVIRONMENT=? order by ENVIRONMENT,TITLE asc", environment);
    }

    public static ArrayList<ServerBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<ServerBean>) SystemDatabase.queryDatabase("KNOWLEDGE_BASE", sql, ServerBean.class, params);
    }
}
