package mysword.test;

import mysword.bean.system.ScheduleBean;
import mysword.system.database.SystemDatabase;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.apache.commons.dbutils.DbUtils.*;

/**
 * Created by Administrator on 2014/6/28.
 */
public class ScheduleScheduleUtils {

    @SuppressWarnings({ "unchecked" })
    public static ArrayList<ScheduleBean> getScheduleList() throws SQLException, ClassNotFoundException {
//        Connection conn = SystemDatabase.getConnection("SCHEDULE");
//        ArrayList<ScheduleBean> result = (ArrayList<ScheduleBean>)SystemDatabase.query(conn, "select * from MYSWORD_SCHEDULE order by LASTEDITDT desc", ScheduleBean.class);
//        closeQuietly(conn);
//        return result;
        return null;
    }

    public static void main(String[] args) throws Exception{
        ArrayList<ScheduleBean> result = getScheduleList();
        for(ScheduleBean sb : result) {
//            System.out.println(sb.isRunning());
//            System.out.println(sb.isStaticMethod());
        }
    }
}
