package mysword.action.project;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.project.ProjectBean;
import mysword.bean.project.ProjectTypeBean;
import mysword.bean.project.ProjectStructureBean;
import mysword.dao.project.ProjectCardDAO;
import mysword.dao.project.ProjectDAO;
import mysword.dao.project.ProjectListFileDAO;
import mysword.system.logger.MyLogger;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProjectListAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private String weekStr;
    private ArrayList<ProjectBean> projectList = new ArrayList<ProjectBean>();
    private ProjectStructureBean[] projectStructures = new ProjectStructureBean[0];
    private ProjectTypeBean projectTypes = new ProjectTypeBean();
    private String startDate;
    private String endDate;
    private String itemIds;
    private InputStream fileStream;
    private File projectFile;
    private int weekNumber = 0;
    private int lastYearWeekCount = 0;
    private int thisYearWeekCount = 0;

    @Override
    public String execute() throws Exception {
        if(StringUtils.isEmpty(weekStr)) {
            weekStr = DateFormatUtils.format(new Date(), "yyyy-w");
        }
        return SUCCESS;
    }

    public String uploadProjectFile() throws SQLException, IOException, ClassNotFoundException {
        if(StringUtils.isEmpty(weekStr)) {
            throw new RuntimeException("There is no week string specify.");
        }
        if(projectFile != null) {
            storeProjectFileToDB();
            MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" upload the project list file.");
        }
        return "input";
    }

    public String syncProjectListFile() throws SQLException, IOException, ClassNotFoundException {
        new ProjectListFileDAO().syncProjectList(weekStr);
        MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" synchronize the project list file.");
        return "success_sync";
    }

    public void projectInfo() throws SQLException, ClassNotFoundException {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        weekNumber = c.get(Calendar.WEEK_OF_YEAR);
        c.add(Calendar.YEAR, c.get(Calendar.YEAR - 1));
        c.set(Calendar.MONTH, 11);
        for(int i=0; i<=7; i++) {
            c.set(Calendar.DAY_OF_MONTH, 30-i);
            if(c.get(Calendar.WEEK_OF_YEAR) != 1) {
                lastYearWeekCount = c.get(Calendar.WEEK_OF_YEAR);
                break;
            }
        }
        c.setTime(new Date());
        c.set(Calendar.MONTH, 11);
        for(int i=0; i<=7; i++) {
            c.set(Calendar.DAY_OF_MONTH, 30-i);
            if(c.get(Calendar.WEEK_OF_YEAR) != 1) {
                thisYearWeekCount = c.get(Calendar.WEEK_OF_YEAR);
                break;
            }
        }
        projectTypes = ProjectDAO.getProjectTypes();
        projectStructures = ProjectDAO.getProejctStructure();
        projectList = ProjectDAO.getProjects(weekStr, null, null, null, null, null, null, null, null, -2, -2);
    }

    public void listByWeek() throws SQLException, ClassNotFoundException {
        projectList = ProjectDAO.getProjects(weekStr, null, null, null, null, null, null, null, null, -2, -2);
    }

    public void updateProject() throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        projectList.get(0).setProjectName(new String(projectList.get(0).getProjectName().getBytes("utf-8")));
        System.out.println(new String(projectList.get(0).getProjectName().getBytes("utf-8")));
        if(projectList != null && projectList.size() != 0) {
            if(ProjectDAO.updateProject(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), projectList.get(0)) == 0) {
                projectList = null;
                MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" update the project item "+projectList.get(0).getItemId()+" fail caused by item has been updated or deleted.");
            } else {
                MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" update the project item "+projectList.get(0).getItemId());
            }
        }
    }

    public String printProjectCards() {
        return "success_print";
    }

    public void addProject() throws SQLException, ClassNotFoundException {
        ProjectDAO.addProjects(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), projectList.get(0));
        MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" add the project item "+projectList.get(0).getItemId());
    }

    public void deleteProject() throws SQLException, ClassNotFoundException {
        ProjectDAO.deleteProjects(projectList.get(0).getItemId());
        MyLogger.sysLogger.info(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username"))+" delete the project item "+projectList.get(0).getItemId());
    }

    public String generateProjectListFile() throws SQLException, IOException, ClassNotFoundException {
        fileStream = new ProjectListFileDAO().generateExcelByWeek(weekStr);
        return "success_excel";
    }

    public String generateProjectCardFile() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<ProjectBean> weekList = ProjectDAO.getProjects(weekStr, null, null, null, null, null, null, null, null, -2, -2);
        ArrayList<ProjectBean> cardList = new ArrayList<ProjectBean>();
        String[] ids = StringUtils.split(itemIds, ";");
        for(String id:ids) {
            for(ProjectBean project1:weekList) {
                if(StringUtils.equals(project1.getItemId(), id)) {
                    cardList.add(0, project1);
                }
            }
        }
        if(cardList.size()>0) {
            fileStream = new ProjectCardDAO().generateCardExcel(cardList.toArray(new ProjectBean[cardList.size()]));
        }
        return "success_cards";
    }

    private void storeProjectFileToDB() throws IOException, SQLException, ClassNotFoundException {
        if(StringUtils.isEmpty(weekStr)) {
            return;
        }
        ArrayList<ProjectBean> pbList = ProjectListFileDAO.phaseScriptFile(new FileInputStream(projectFile));
        if(pbList!=null && pbList.size()>0) {
            ProjectBean[] pbArray = new ProjectBean[pbList.size()];
            for (int i=0; i<pbList.size(); i++) {
                pbList.get(i).setWeek(weekStr);
                pbArray[i] = pbList.get(i);
            }
            ProjectDAO.addProjects(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), pbArray);
        }
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public File getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(File projectFile) {
        this.projectFile = projectFile;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public ArrayList<ProjectBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<ProjectBean> projectList) {
        this.projectList = projectList;
    }

    public ProjectStructureBean[] getProjectStructures() {
        return projectStructures;
    }

    public void setProjectStructures(ProjectStructureBean[] projectStructures) {
        this.projectStructures = projectStructures;
    }

    public ProjectTypeBean getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(ProjectTypeBean projectTypes) {
        this.projectTypes = projectTypes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getLastYearWeekCount() {
        return lastYearWeekCount;
    }

    public void setLastYearWeekCount(int lastYearWeekCount) {
        this.lastYearWeekCount = lastYearWeekCount;
    }

    public int getThisYearWeekCount() {
        return thisYearWeekCount;
    }

    public void setThisYearWeekCount(int thisYearWeekCount) {
        this.thisYearWeekCount = thisYearWeekCount;
    }
}
