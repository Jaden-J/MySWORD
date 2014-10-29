package mysword.bean.system;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by Administrator on 2014/8/24.
 */
public class SystemDatabaseBean {
    private String databaseName;
    private String databaseType;
    private String driverClass;
    private String databaseURL;
    private String username;
    private String password;
    private String description;
    private int timeout=3000;
    private int maxThread =5;
    private int currentThread=0;
    private String lasteditby="Administrator";
    private String lasteditdt=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getCurrentThread() {
        return currentThread;
    }

    public void setCurrentThread(int currentThread) {
        this.currentThread = currentThread;
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
}
