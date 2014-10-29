package mysword.dao.project;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mysword.bean.project.ProjectBean;
import mysword.bean.project.ProjectTypeBean;
import mysword.bean.project.ProjectStructureBean;
import mysword.system.conf.SystemConf;
import mysword.system.database.SystemDatabase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ProjectDAO {

    public static String getProjectStructureConfigFile() {
        return SystemConf.CONF_HOME + "project.xml";
    }

    public static String getProjectTypeConfigFile() {
        return SystemConf.CONF_HOME + "project_type.xml";
    }

    public static ProjectTypeBean getProjectTypes() {
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectTypeBean.class);
        xs.alias("name", String.class);
        return (ProjectTypeBean) xs.fromXML(new File(getProjectTypeConfigFile()));
    }

    public static void saveProjectTypes(ProjectTypeBean pdb) throws IOException {
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectTypeBean.class);
        xs.alias("name", String.class);
        FileUtils.writeStringToFile(new File(getProjectTypeConfigFile()), xs.toXML(pdb));
    }

    public static ProjectStructureBean[] getProejctStructure() {
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectStructureBean.class);
        xs.alias("typeName", String.class);
        xs.alias("ProjectTypeList", ProjectStructureBean[].class);
        return (ProjectStructureBean[]) xs.fromXML(new File(getProjectStructureConfigFile()));
    }

    public static void saveProjectStructure(ProjectStructureBean[] ptb) throws IOException {
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectStructureBean.class);
        xs.alias("typeName", String.class);
        xs.alias("ProjectTypeList", ProjectStructureBean[].class);
        FileUtils.writeStringToFile(new File(getProjectStructureConfigFile()), xs.toXML(ptb));
    }

    public static int updateProject(String requester, ProjectBean pb) throws SQLException, ClassNotFoundException {
        String oldLockId = pb.getLockId();
        pb.setLockId(UUID.randomUUID().toString());
        pb.setLastEditBy(requester);
        pb.setLastEditDT(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return updateDatabase("update MYSWORD_PROJECT_LIST set LOCKID=?,REGION=?,ITEM_TYPE=?,SCRIPTID=?,GSD=?,PROJECTNAME=?,CATEGORY=?,COORDINATOR=?,CUSTOMER=?,DOCLINK=?,LIVEONTEST=?,LIVEONPROD=?," +
                        "PRIORITY=?,ITEM_COMMENT=?,DEVELOPER=?,ESTIMATEEFFORT=?,REALEFFORT=?,PENDINGEFFORT=?,RESTEFFORT=?,UNDERESTIMATEDEFFORT=?,LASTEDITBY=?,LASTEDITDT=? where ITEMID=? and LOCKID=?",
                pb.getLockId(),
                pb.getRegion(),
                pb.getItem_Type(),
                pb.getScriptId(),
                pb.getGSD(),
                pb.getProjectName(),
                pb.getCategory(),
                pb.getCoordinator(),
                pb.getCustomer(),
                pb.getDocLink(),
                pb.getLiveOnTest(),
                pb.getLiveOnProd(),
                pb.getPriority(),
                pb.getItem_Comment(),
                pb.getDeveloper(),
                String.valueOf(pb.getEstimateEffort()),
                String.valueOf(pb.getRealEffort()),
                String.valueOf(pb.getPendingEffort()),
                String.valueOf(pb.getRestEffort()),
                String.valueOf(pb.getUnderEstimatedEffort()),
                pb.getLastEditBy(),
                pb.getLastEditDT(),
                pb.getItemId(),
                oldLockId
        );
    }

    public static int addProjects(String requester, ProjectBean... pbList) throws SQLException, ClassNotFoundException {
        if(pbList == null || pbList.length == 0) {
            return 0;
        }
        Object[][] params = new Object[pbList.length][25];
        for(int i=0; i<pbList.length; i++) {
            pbList[i].setItemId(UUID.randomUUID().toString());
            params[i][0] = pbList[i].getItemId();
            pbList[i].setLockId(UUID.randomUUID().toString());
            params[i][1] = pbList[i].getLockId();
            params[i][2] = pbList[i].getWeek();
            params[i][3] = pbList[i].getRegion();
            params[i][4] = pbList[i].getItem_Type();
            params[i][5] = pbList[i].getScriptId();
            params[i][6] = pbList[i].getGSD();
            params[i][7] = pbList[i].getProjectName();
            params[i][8] = String.valueOf(pbList[i].getEstimateEffort());
            params[i][9] = String.valueOf(pbList[i].getRealEffort());
            params[i][10] = String.valueOf(pbList[i].getPendingEffort());
            params[i][11] = String.valueOf(pbList[i].getRestEffort());
            params[i][12] = String.valueOf(pbList[i].getUnderEstimatedEffort());
            params[i][13] = pbList[i].getItem_Comment();
            params[i][14] = pbList[i].getDeveloper();
            pbList[i].setLastEditBy(requester);
            params[i][15] = pbList[i].getLastEditBy();
            pbList[i].setLastEditDT(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            params[i][16] = pbList[i].getLastEditDT();
            params[i][17] = pbList[i].getCategory();
            params[i][18] = pbList[i].getCustomer();
            params[i][19] = pbList[i].getCoordinator();
            params[i][20] = pbList[i].getDocLink();
            params[i][21] = pbList[i].getLiveOnProd();
            params[i][22] = pbList[i].getLiveOnTest();
            params[i][23] = pbList[i].getPriority();
            params[i][24] = pbList[i].getMaps();
        }
        return batchUpdateDatabase("insert into MYSWORD_PROJECT_LIST(ITEMID,LOCKID,WEEK,REGION,ITEM_TYPE,SCRIPTID,GSD,PROJECTNAME,ESTIMATEEFFORT,REALEFFORT,PENDINGEFFORT,RESTEFFORT," +
            "UNDERESTIMATEDEFFORT,ITEM_COMMENT,DEVELOPER,LASTEDITBY,LASTEDITDT,CATEGORY,CUSTOMER,COORDINATOR,DOCLINK,LIVEONPROD,LIVEONTEST,PRIORITY,MAPS) values(?,?,?,?,?,?,?,?,?,?,?,?,?," +
            "?,?,?,?,?,?,?,?,?,?,?,?)",
            params);
    }

    public static ProjectBean getProjectById(String itemId) throws SQLException, ClassNotFoundException {
        ArrayList<ProjectBean> result  = queryDatabase("select * from MYSWORD_PROJECT_LIST where ITEMID=?", itemId);
        if(result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static int deleteProjects(String... itemId) throws SQLException, ClassNotFoundException {
        if(itemId == null || itemId.length == 0) {
            return 0;
        }
        Object[][] params = new Object[itemId.length][1];
        for(int i = 0; i<itemId.length; i++) {
            params[i][0] = itemId[i];
        }
        return batchUpdateDatabase("delete from MYSWORD_PROJECT_LIST where ITEMID=?", params);
    }

    public static ArrayList<ProjectBean> getProjects(String week, String region, String item_Type, String gsd, String projectName, String developer, String comment, String startDate, String endDate,
                                                     int start, int length) throws SQLException, ClassNotFoundException {
        String sql = "select * from MYSWORD_PROJECT_LIST ";
        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotEmpty(week)) {
            String weekCon = " and WEEK='" + week + "'";
            condition.append(weekCon);
        }
        if (StringUtils.isNotEmpty(region)) {
            String regionCon = " and REGION='" + region + "'";
            condition.append(regionCon);
        }
        if (StringUtils.isNotEmpty(item_Type)) {
            String typeCon = " and ITEM_TYPE='" + item_Type + "'";
            condition.append(typeCon);
        }
        if (StringUtils.isNotEmpty(gsd)) {
            String gsdCon = " and GSD='" + gsd + "'";
            condition.append(gsdCon);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            String projectNameCon = " and PROJECTNAME like'%" + projectName + "%'";
            condition.append(projectNameCon);
        }
        if (StringUtils.isNotEmpty(developer)) {
            String developerCon = " and DEVELOPER='" + developer + "'";
            condition.append(developerCon);
        }
        if (StringUtils.isNotEmpty(comment)) {
            String commentCon = " and ITEM_COMMENT='" + comment + "'";
            condition.append(commentCon);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            String startDateCon = " and STARTDATE>'" + startDate + "'";
            condition.append(startDateCon);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            String endDateCon = " and ENDDATE>'" + endDate + "'";
            condition.append(endDateCon);
        }
        if (StringUtils.isNotEmpty(condition.toString())) {
            sql += "where" + condition.toString().replaceFirst(" and", "");
        }
        if (start >= -1 && length >= 0) {
            sql += " limit " + start + "," + length;
        }
        sql += " order by GSD,SCRIPTID,LASTEDITDT asc";
        return queryDatabase(sql);
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<ProjectBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<ProjectBean>) SystemDatabase.queryDatabase("PROJECT_LIST", sql, ProjectBean.class, params);
    }

    @SuppressWarnings("unchecked")
    private static int updateDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.updateDatabase("PROJECT_LIST", sql, params);
    }

    @SuppressWarnings("unchecked")
    private static int batchUpdateDatabase(String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.batchUpdateDatabase("PROJECT_LIST", sql, params);
    }
}
