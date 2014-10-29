package mysword.bean.system;

import java.util.TimerTask;

public class ScheduleStatusBean {

	private String scheduleId;
	private String schedule_Name;
	private String schedule_Service;
	private String schedule_Type;
	private String active;
	private String run_Mode;
	private String running;
	private String thread;
	private String interval;
	private String run_Time;
	private String month;
	private String day;
	private String hour;
	private String lastrundt;
	private String nextRun;
	private String lasteditby;
	private String lasteditdt;
	private TimerTask task;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRun_Mode() {
		return run_Mode;
	}
	public void setRun_Mode(String run_Mode) {
		this.run_Mode = run_Mode;
	}
	public String getRunning() {
		return running;
	}
	public void setRunning(String running) {
		this.running = running;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getRun_Time() {
		return run_Time;
	}
	public void setRun_Time(String run_Time) {
		this.run_Time = run_Time;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getLastrundt() {
		return lastrundt;
	}
	public void setLastrundt(String lastrundt) {
		this.lastrundt = lastrundt;
	}
	public String getNextRun() {
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
	public TimerTask getTask() {
		return task;
	}
	public void setTask(TimerTask task) {
		this.task = task;
	}
}
