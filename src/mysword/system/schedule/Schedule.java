package mysword.system.schedule;

import mysword.bean.system.ScheduleBean;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class Schedule implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String scheduleId = context.getJobDetail().getKey().getName();
        try {
            ScheduleBean schedule = SystemSchedule.getScheudle(scheduleId);
            if (StringUtils.isEmpty(schedule.getSchedule_Name())) {
                return;
            }
            if (schedule.getThread() > 0 && !schedule.isMultiple()) {
                MyLogger.sysLogger.debug(schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "]" + " is running, cancel!");
                return;
            }
            MyLogger.sysLogger.debug(schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "]" + " start.");
            String serviceName = schedule.getSchedule_Service().substring(0, schedule.getSchedule_Service().lastIndexOf(":"));
            String methodName = schedule.getSchedule_Service().substring(schedule.getSchedule_Service().lastIndexOf(":") + 1);
            SystemSchedule.increaseThread(scheduleId);
            SystemSchedule.setLastRunDate(scheduleId, new Date());
            if (schedule.isStaticMethod()) {
                MethodUtils.invokeExactStaticMethod(Class.forName(serviceName), methodName);
            } else {
                Object classObj = Class.forName(serviceName).newInstance();
                MethodUtils.invokeMethod(classObj, methodName);
            }
            MyLogger.sysLogger.debug(schedule.getSchedule_Name() + "[" + schedule.getScheduleId() + "]" + " end.");
            SystemSchedule.decreaseThread(scheduleId);
        } catch (Exception e) {
            try {
                SystemSchedule.decreaseThread(scheduleId);
            }catch (Exception e1) {
                MyLogger.sysLogger.error(MyLogger.getErrorString(e1));
                e1.printStackTrace();
            }
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            e.printStackTrace();
        }
    }
}
