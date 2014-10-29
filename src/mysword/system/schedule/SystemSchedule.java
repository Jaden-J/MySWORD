package mysword.system.schedule;

import mysword.bean.system.ScheduleBean;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

@SuppressWarnings("unused")
public class SystemSchedule {

    public static Scheduler systemSchedule;

    static {
        try {
            systemSchedule = StdSchedulerFactory.getDefaultScheduler();
            systemSchedule.start();
        } catch (Exception e) {
            MyLogger.sysLogger.error("System cannot initial the schedule system, kindly please contact Admin to check the root cause!");
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<ScheduleBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<ScheduleBean>)SystemDatabase.queryDatabase("SYSTEM", sql, ScheduleBean.class, params);
    }

    private static int updateDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.updateDatabase("SYSTEM", sql, params);
    }

    public static void activateSchedule(String scheduleId, String username) throws SQLException, ClassNotFoundException, ParseException, SchedulerException {
        ScheduleBean schedule = getScheudle(scheduleId);
        if (schedule != null) {
            updateDatabase("update MYSWORD_SCHEDULE set ACTIVE=1, LASTEDITBY=?, LASTEDITDT=? where SCHEDULEID=? and ACTIVE=0", username, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"), scheduleId);
            if(!schedule.isActive()) {
                schedule.setActive(true);
                runSchedule(schedule);
            }
            MyLogger.sysLogger.info(username + " activate schedule " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "].");
        }
    }

    public static void runSchedule(ScheduleBean schedule) throws SchedulerException, ParseException {
        if (schedule.isActive()) {
            JobDetail job = JobBuilder.newJob(Schedule.class).withIdentity(schedule.getScheduleId(), schedule.getSchedule_Name()).build();
            Trigger trigger;
            if ("Repeat".equals(schedule.getSchedule_Type())) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(schedule.getScheduleId(), schedule.getSchedule_Name())
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(schedule.getSchedule_Interval())
                                .repeatForever())
                        .build();
                systemSchedule.scheduleJob(job, trigger);
                MyLogger.sysLogger.debug("Add repeat schedule: " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "], interval:" + schedule.getSchedule_Interval());
            } else if ("Onetime".equals(schedule.getSchedule_Type())) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(schedule.getRun_Time());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(schedule.getScheduleId(), schedule.getSchedule_Name())
                        .startNow()
                        .withSchedule(CronScheduleBuilder.cronSchedule(DateFormatUtils.format(date, "s m H d M ? yyyy")))
                        .build();
                if (date.after(new Date())) {
                    systemSchedule.scheduleJob(job, trigger);
                    MyLogger.sysLogger.debug("Add onetime schedule: " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "], run time: " + schedule.getRun_Time());
                }
            } else if ("Complex".equals(schedule.getSchedule_Type())) {
                String setting = "0 " + schedule.getMinute_of_hour() + " " + schedule.getHour_of_day() + " " + schedule.getDay_of_month() + " " + schedule.getMonth_of_year() + " " + schedule.getDay_of_week()
                    + " " + Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.YEAR) + 10);
                trigger = TriggerBuilder.newTrigger()
                    .withIdentity(schedule.getScheduleId(), schedule.getSchedule_Name()).startNow().withSchedule(CronScheduleBuilder.cronSchedule(setting)).build();
                systemSchedule.scheduleJob(job, trigger);
                MyLogger.sysLogger.debug("Add complex schedule: " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "], settings: " + setting);
            }
        }
    }

    public static void createSchedule(ScheduleBean schedule, String username) throws SQLException, ClassNotFoundException, ParseException, SchedulerException {
        if (schedule == null) {
            return;
        }
        schedule.setScheduleId(UUID.randomUUID().toString());
        updateDatabase(
                "insert into MYSWORD_SCHEDULE(SCHEDULEID,SCHEDULE_NAME,SCHEDULE_SERVICE,SCHEDULE_TYPE,ACTIVE,MULTIPLE,STATICMETHOD,SCHEDULE_INTERVAL,RUN_TIME,MINUTE_OF_HOUR,HOUR_OF_DAY," +
                        "DAY_OF_MONTH,MONTH_OF_YEAR,DAY_OF_WEEK,LASTEDITBY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                schedule.getScheduleId(),
                schedule.getSchedule_Name(),
                schedule.getSchedule_Service(),
                schedule.getSchedule_Type(),
                (schedule.isActive() ? 1 : 0),
                (schedule.isMultiple() ? 1 : 0),
                (schedule.isStaticMethod() ? 1 : 0),
                schedule.getSchedule_Interval(),
                schedule.getRun_Time(),
                schedule.getMinute_of_hour(),
                schedule.getHour_of_day(),
                schedule.getDay_of_month(),
                schedule.getMonth_of_year(),
                schedule.getDay_of_week(),
                username);
        MyLogger.sysLogger.info(username + " add schedule " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "] to database.");
        runSchedule(schedule);
    }

    public static void deactivateSchedule(String scheduleId, String username) throws SQLException, ClassNotFoundException, SchedulerException {
        ScheduleBean schedule = getScheudle(scheduleId);
        if (schedule != null) {
            systemSchedule.deleteJob(new JobKey(schedule.getScheduleId(), schedule.getSchedule_Name()));
            updateDatabase("update MYSWORD_SCHEDULE set ACTIVE=0, LASTEDITBY=?, LASTEDITDT=? where SCHEDULEID=?", username, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"), scheduleId);
            schedule.setActive(false);
            MyLogger.sysLogger.info(username + " deactivate schedule " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "].");
        }
    }

    public static void deleteSchedule(String scheduleId, String username) throws SchedulerException, SQLException, ClassNotFoundException {
        ScheduleBean schedule = getScheudle(scheduleId);
        if (schedule != null) {
            systemSchedule.deleteJob(new JobKey(schedule.getScheduleId(), schedule.getSchedule_Name()));
            updateDatabase("delete from MYSWORD_SCHEDULE where SCHEDULEID=?", scheduleId);
            MyLogger.sysLogger.info(username + " delete schedule " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "].");
        }
    }

    public static ScheduleBean getScheudle(String scheduleId) throws SQLException, ClassNotFoundException {
        ArrayList<ScheduleBean> resultList = queryDatabase("select * from MYSWORD_SCHEDULE where SCHEDULEID=?", scheduleId);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public static ArrayList<ScheduleBean> getScheduleList() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_SCHEDULE order by LASTEDITDT");
    }

    public static ArrayList<ScheduleBean> getScheduleList(String scheduleType) throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_SCHEDULE where SCHEDULE_TYPE=? order by SCHEDULE_TYPE", scheduleType);
    }

    public static int setRunStatus(String scheduleId, boolean status) throws SQLException, ClassNotFoundException {
        return updateDatabase("update MYSWORD_SCHEDULE set RUNNING=? where SCHEDULEID=?", status?1:0, scheduleId);
    }

    public static int increaseThread(String scheduleId) throws SQLException, ClassNotFoundException {
        return updateDatabase("update MYSWORD_SCHEDULE set THREAD=THREAD+1 where SCHEDULEID=?", scheduleId);
    }

    public static int decreaseThread(String scheduleId) throws SQLException, ClassNotFoundException {
        return updateDatabase("update MYSWORD_SCHEDULE set THREAD=THREAD-1 where SCHEDULEID=?", scheduleId);
    }

    public static int initThread(String scheduleId) throws SQLException, ClassNotFoundException {
        return updateDatabase("update MYSWORD_SCHEDULE set THREAD=0 where SCHEDULEID=?", scheduleId);
    }

    public static int setLastRunDate(String scheduleId, Date date) throws SQLException, ClassNotFoundException {
        return updateDatabase("update MYSWORD_SCHEDULE set LASTRUNDT=? where SCHEDULEID=?", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS"), scheduleId);
    }

    public static void initSchedule() throws Exception {
        MyLogger.sysLogger.debug("System schedule initializing.");
        ArrayList<ScheduleBean> schedules = getScheduleList();
        for (ScheduleBean schedule : schedules) {
            initThread(schedule.getScheduleId());
            runSchedule(schedule);
        }
        MyLogger.sysLogger.debug("System scheduler initialize successfully.");
    }

    public static void updateSchedule(ScheduleBean schedule, String username) throws SQLException, SchedulerException, ClassNotFoundException, ParseException {
        if (schedule == null) {
            return;
        }
        systemSchedule.deleteJob(new JobKey(schedule.getScheduleId(), schedule.getSchedule_Name()));
        if(StringUtils.isEmpty(schedule.getMinute_of_hour())) {
            schedule.setMinute_of_hour("*");
        }
        if(StringUtils.isEmpty(schedule.getHour_of_day())) {
            schedule.setHour_of_day("*");
        }
        if(StringUtils.isEmpty(schedule.getDay_of_month())) {
            if(StringUtils.isEmpty(schedule.getDay_of_week())) {
                schedule.setDay_of_month("*");
                schedule.setDay_of_week("?");
            } else {
                schedule.setDay_of_month("?");
            }
        } else {
            schedule.setDay_of_week("?");
        }
        if(StringUtils.isEmpty(schedule.getMonth_of_year())) {
            schedule.setMonth_of_year("*");
        }
        if(StringUtils.isEmpty(schedule.getDay_of_week())) {
            schedule.setMonth_of_year("*");
        }
        updateDatabase("update MYSWORD_SCHEDULE set SCHEDULE_NAME=?,SCHEDULE_SERVICE=?,SCHEDULE_TYPE=?,ACTIVE=?,MULTIPLE=?,STATICMETHOD=?,SCHEDULE_INTERVAL=?,RUN_TIME=?,MINUTE_OF_HOUR=?," +
                        "HOUR_OF_DAY=?,DAY_OF_MONTH=?,MONTH_OF_YEAR=?,DAY_OF_WEEK=?,LASTEDITBY=?,LASTEDITDT=? where SCHEDULEID=?",
            schedule.getSchedule_Name(),
            schedule.getSchedule_Service(),
            schedule.getSchedule_Type(),
            (schedule.isActive() ? 1 : 0),
            (schedule.isMultiple() ? 1 : 0),
            (schedule.isStaticMethod() ? 1 : 0),
            schedule.getSchedule_Interval(),
            schedule.getRun_Time(),
            schedule.getMinute_of_hour(),
            schedule.getHour_of_day(),
            schedule.getDay_of_month(),
            schedule.getMonth_of_year(),
            schedule.getDay_of_week(),
            username,
            DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"),
            schedule.getScheduleId());
        if (schedule.isActive()) {
            runSchedule(schedule);
        }
        MyLogger.sysLogger.info(username + " update schedule " + schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "].");
    }
}
