package mysword.dao.knowledgebase;

import mysword.bean.knowledgebase.ComsysBean;
import mysword.system.database.SystemDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hyao on 7/25/2014.
 */
public class ComsysDAO {
    public static ArrayList<ComsysBean> getComsysList() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_COMSYS(nolock) order by ENVIRONMENT,INSTANCE asc");
    }

    public static ArrayList<ComsysBean> getComsysByEnv(String environment) throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_COMSYS(nolock) where ENVIRONMENT=? order by ENVIRONMENT,INSTANCE asc", environment);
    }

    public static ArrayList<ComsysBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<ComsysBean>) SystemDatabase.queryDatabase("KNOWLEDGE_BASE", sql, ComsysBean.class, params);
    }
}
