package mysword.action.system;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.system.ScheduleBean;
import mysword.system.schedule.SystemSchedule;
import mysword.utils.ServletActionContextUtils;
import org.quartz.SchedulerException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ScheduleAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private ArrayList<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

	public ArrayList<ScheduleBean> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(ArrayList<ScheduleBean> scheduleList) {
		this.scheduleList = scheduleList;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    public void listSchedules() throws SQLException, ClassNotFoundException {
        scheduleList = SystemSchedule.getScheduleList();
    }

    public void createSchedule() throws ParseException, SchedulerException, SQLException, ClassNotFoundException {
        SystemSchedule.createSchedule(scheduleList.get(0), String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
    }

    public void updateSchedule() throws ClassNotFoundException, SQLException, ParseException, SchedulerException {
        SystemSchedule.updateSchedule(scheduleList.get(0), String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
    }

    public void deleteSchedule() throws ClassNotFoundException, SchedulerException, SQLException {
        SystemSchedule.deleteSchedule(scheduleList.get(0).getScheduleId(), String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
    }

    public void switchSchedule() throws ClassNotFoundException, SQLException, SchedulerException, ParseException {
        if(scheduleList.get(0).isActive()) {
            SystemSchedule.activateSchedule(scheduleList.get(0).getScheduleId(), String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
        } else {
            SystemSchedule.deactivateSchedule(scheduleList.get(0).getScheduleId(), String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")));
        }
    }
}
