package mysword.bean.system;

import mysword.system.schedule.SystemSchedule;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class ScheduleBean {

	private String scheduleId;
	private String schedule_Name;
	private String schedule_Service;
	private String schedule_Type;
	private boolean active = false;
	private boolean multiple = false;
	private boolean running = false;
	private boolean staticMethod = false;
	private int schedule_Interval =300;
	private int thread=0;
	private String run_Time;
	private String month_of_year;
	private String day_of_month;
	private String hour_of_day;
	private String minute_of_hour;
	private String day_of_week;
	private String lastrundt;
	private String nextRun;
	private String lasteditby;
	private String lasteditdt;
	private ArrayList<TimerTask> task = new ArrayList<TimerTask>();
	
	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getSchedule_Name() {
		return schedule_Name;
	}

	public void setSchedule_Name(String schedule_Name) {
		this.schedule_Name = schedule_Name;
	}

	public String getSchedule_Service() {
		return schedule_Service;
	}

	public void setSchedule_Service(String schedule_Service) {
		this.schedule_Service = schedule_Service;
	}

	public String getSchedule_Type() {
		return schedule_Type;
	}

	public void setSchedule_Type(String schedule_Type) {
		this.schedule_Type = schedule_Type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getSchedule_Interval() {
		return schedule_Interval;
	}

	public boolean isStaticMethod() {
		return staticMethod;
	}

	public void setStaticMethod(boolean staticMethod) {
		this.staticMethod = staticMethod;
	}

	public void setSchedule_Interval(int schedule_Interval) {
		this.schedule_Interval = schedule_Interval;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String getRun_Time() {
		return run_Time;
	}

	public void setRun_Time(String run_Time) {
		this.run_Time = run_Time;
	}

	public String getMonth_of_year() {
		return month_of_year;
	}

	public void setMonth_of_year(String month_of_year) {
		this.month_of_year = month_of_year;
	}

	public String getDay_of_month() {
		return day_of_month;
	}

	public void setDay_of_month(String day_of_month) {
		this.day_of_month = day_of_month;
	}

	public String getHour_of_day() {
		return hour_of_day;
	}

	public void setHour_of_day(String hour_of_day) {
		this.hour_of_day = hour_of_day;
	}

	public String getMinute_of_hour() {
		return minute_of_hour;
	}

	public void setMinute_of_hour(String minute_of_hour) {
		this.minute_of_hour = minute_of_hour;
	}

	public String getDay_of_week() {
		return day_of_week;
	}

	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}

	public String getLastrundt() {
		return lastrundt;
	}

	public void setLastrundt(String lastrundt) {
		this.lastrundt = lastrundt;
	}

	public String getNextRun() throws Exception {
		nextRun = nextRunDate();
		return nextRun;
	}

	public void setNextRun(String nextRun) {
		this.nextRun = nextRun;
	}

	public String getLasteditby() {
		return lasteditby;
	}

	public void setLasteditby(String lasteditby) {
		this.lasteditby = lasteditby;
	}

	public String getLasteditdt() {
		return lasteditdt;
	}

	public void setLasteditdt(String lasteditdt) {
		this.lasteditdt = lasteditdt;
	}

	public ArrayList<TimerTask> getTask() {
		return task;
	}

	public void setTask(ArrayList<TimerTask> task) {
		this.task = task;
	}
	
	public void addTask(TimerTask task) {
		this.task.add(task);
	}

	private String nextRunDate() throws Exception {
        if (!active) {
            return "";
        }
        Trigger trigger = SystemSchedule.systemSchedule.getTrigger(new TriggerKey(scheduleId, schedule_Name));
        if (trigger == null) {
            return "";
        }
        Date nextRunDate = null;
        if ("Repeat".equals(schedule_Type)) {
            nextRunDate = trigger.getNextFireTime();
        } else if("Onetime".equals(schedule_Type)){
            return run_Time;
        }else {
			CronTrigger trigger1 = (CronTrigger)trigger;
			CronExpression cron = new CronExpression(trigger1.getCronExpression());
			nextRunDate = cron.getNextValidTimeAfter(new Date());
		}
		if(nextRunDate == null) {
			return "";
		} else {
			return DateFormatUtils.format(nextRunDate, "yyyy-MM-dd HH:mm:ss");
		}
	}
}
