package mysword.system.conf;

import mysword.bean.system.SystemConfBean;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.util.Base64;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

@SuppressWarnings("unused")
public class SystemConf {

    public static Properties MAIL_PROPS;
    public final static String ROOT_PATH = new SystemConf().getClass().getClassLoader().getResource("/").getPath().replace("\\", "/").replace("%20", " ").replaceAll("/WEB-INF/classes/", "/");
    public static String SERVER_NAME;
    public static String SERVER_IP;
    public static int SERVER_PORT;
    public static String CONTEXT_PATH;
	public final static String CONF_HOME = ROOT_PATH + "WEB-INF/conf/";
    public final static String DATABASE_HOME = ROOT_PATH + "WEB-INF/conf/database/";

    public static String POP3_HOSTNAME;
    public static String POP3_USERNAME;
    public static String POP3_PASSWORD;
    public static int POP3_PORT;
    public static String SMTP_HOSTNAME;
    public static String SMTP_USERNAME;
    public static String SMTP_PASSWORD;
    public static int SMTP_PORT;
    public static String EMAIL_FOLDER;
    public static String DEFAULT_FROM;
    public static String KNOWLEDGE_SHARING_GROUP;

	public static HashMap<String, String> props = new HashMap<String, String>();

    @SuppressWarnings("unchecked")
    private static ArrayList<SystemConfBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<SystemConfBean>)SystemDatabase.queryDatabase("SYSTEM", sql, SystemConfBean.class, params);
    }

    private static int updateDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.updateDatabase("SYSTEM", sql, params);
    }

    private static int batchUpdateDatabase(String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        return SystemDatabase.batchUpdateDatabase("SYSTEM", sql, params);
    }

	public static void initConfig() throws SQLException, ClassNotFoundException, UnknownHostException {
        SERVER_NAME = InetAddress.getLocalHost().getHostName();
        SERVER_IP = InetAddress.getLocalHost().getHostAddress();
        ArrayList<SystemConfBean> configs = listConfigDetail();
        if(configs!=null) {
            for (SystemConfBean scb : configs) {
                props.put(scb.getConfig_name(), StringUtils.trimToEmpty(scb.getConfig_value()));
            }
        }
        MAIL_PROPS = new Properties();
        SERVER_PORT = NumberUtils.toInt(getConfString("server.port"), 8080);
        CONTEXT_PATH = getConfString("server.system.context.path");
        POP3_HOSTNAME = getConfString("server.email.pop3.hostname");
        POP3_USERNAME = new String(Base64.decodeBase64(getConfString("server.email.pop3.username")));
        POP3_PASSWORD = new String(Base64.decodeBase64(getConfString("server.email.pop3.password")));
        POP3_PORT = NumberUtils.toInt(getConfString("server.email.pop3.port"), 110);
        SMTP_HOSTNAME = getConfString("server.email.smtp.hostname");
        SMTP_USERNAME = new String(Base64.decodeBase64(getConfString("server.email.smtp.username")));
        SMTP_PASSWORD = new String(Base64.decodeBase64(getConfString("server.email.smtp.password")));
        SMTP_PORT = NumberUtils.toInt(getConfString("server.email.smtp.port"), 25);
        DEFAULT_FROM = getConfString("server.email.from");
        KNOWLEDGE_SHARING_GROUP = getConfString("server.knowledge.sharing.group");
        MAIL_PROPS.setProperty("mail.pop3.host", POP3_HOSTNAME);
        MAIL_PROPS.setProperty("mail.smtp.port", String.valueOf(POP3_PORT));
        MAIL_PROPS.setProperty("mail.pop3.auth", getConfString("server.email.pop3.auth"));
        MAIL_PROPS.setProperty("mail.smtp.host", SMTP_HOSTNAME);
        MAIL_PROPS.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        MAIL_PROPS.setProperty("mail.smtp.auth", getConfString("server.email.smtp.auth"));
        MAIL_PROPS.setProperty("mail.store.protocol", getConfString("server.email.store.protocol"));
        MAIL_PROPS.setProperty("mail.transport.protocol", getConfString("server.email.transport.protocol"));
        MAIL_PROPS.setProperty("mail.from", getConfString("server.email.from"));
        MAIL_PROPS.setProperty("mail.debug", getConfString("server.email.debug"));
        String backupPath = getConfString("server.email.default.folder");
        if(StringUtils.endsWithAny(backupPath, "/", "\\")) {
            EMAIL_FOLDER = backupPath;
        } else if (StringUtils.isEmpty(backupPath)){
            EMAIL_FOLDER = "";
        } else {
            EMAIL_FOLDER = backupPath + "/";
        }
        MyLogger.sysLogger.info("Initial config successfully!");
	}

    public static int addConfig(String username, SystemConfBean config) throws SQLException, ClassNotFoundException, UnknownHostException {
        if(config == null) {
            return 0;
        }
        config.setLasteditby(username);
        config.setLasteditdt(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        int result = updateDatabase("insert into MYSWORD_CONFIG(CONFIG_NAME,CONFIG_VALUE,CONFIG_TYPE,CONFIG_DESC,CONFIG_REQ,LASTEDITBY,LASTEDITDT) values(?,?,?,?,?,?,?)",
            config.getConfig_name(),
            config.getConfig_value(),
            config.getConfig_type(),
            config.getConfig_desc(),
            config.isConfig_req()?1:0,
            config.getLasteditby(),
            config.getLasteditdt()
        );
        MyLogger.sysLogger.info(username + " add config: " + config.getConfig_name());
        initConfig();
        return result;
    }

    public static int addConfigs(String username, SystemConfBean... configList) throws SQLException, ClassNotFoundException, UnknownHostException {
        if(configList == null || configList.length < 1) {
            return 0;
        }
        Object[][] params = new Object[configList.length][7];
        String dateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        StringBuilder info = new StringBuilder();
        for(int i=0; i<configList.length; i++) {
            params[i][0] = configList[i].getConfig_name();
            params[i][1] = configList[i].getConfig_value();
            params[i][2] = configList[i].getConfig_type();
            params[i][3] = configList[i].getConfig_desc();
            params[i][4] = configList[i].isConfig_req();
            configList[i].setLasteditby(username);
            params[i][5] = username;
            configList[i].setLasteditdt(dateStr);
            params[i][6] = dateStr;
            String temp = configList[i].getConfig_name() + "\n";
            info.append(temp);
        }
        int result = batchUpdateDatabase("insert into MYSWORD_CONFIG(CONFIG_NAME,CONFIG_VALUE,CONFIG_TYPE,CONFIG_DESC,CONFIG_REQ,LASTEDITBY,LASTEDITDT) with(rowlock) values(?,?,?,?,?,?,?)", params);
        MyLogger.sysLogger.info(username + " add configs: \n" + info);
        initConfig();
        return result;
    }

    public static int updateConfig(String username, SystemConfBean config) throws SQLException, ClassNotFoundException, UnknownHostException {
        if(config == null) {
            return 0;
        }
        config.setLasteditby(username);
        config.setLasteditdt(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        int result = updateDatabase("update MYSWORD_CONFIG with(rowlock) set CONFIG_VALUE=?,CONFIG_TYPE=?,CONFIG_DESC=?,CONFIG_REQ=?,LASTEDITBY=?,LASTEDITDT=? where CONFIG_NAME=?",
                config.getConfig_value(),
                config.getConfig_type(),
                config.getConfig_desc(),
                config.isConfig_req()?1:0,
                config.getLasteditby(),
                config.getLasteditdt(),
                config.getConfig_name()
        );
        MyLogger.sysLogger.info(username + " update config: " + config.getConfig_name());
        initConfig();
        return result;
    }

    public static int updateConfigs(String username, SystemConfBean... configList) throws SQLException, ClassNotFoundException, UnknownHostException {
        if(configList == null || configList.length < 1) {
            return 0;
        }
        Object[][] params = new Object[configList.length][7];
        String dateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        StringBuilder info = new StringBuilder();
        for(int i=0; i<configList.length; i++) {
            params[i][0] = configList[i].getConfig_value();
            params[i][1] = configList[i].getConfig_type();
            params[i][2] = configList[i].getConfig_desc();
            params[i][3] = configList[i].isConfig_req();
            params[i][4] = username;
            params[i][5] = dateStr;
            params[i][6] = configList[i].getConfig_name();
            String temp = configList[i].getConfig_name() + "\n";
            info.append(temp);
        }
        int result = batchUpdateDatabase("update MYSWORD_CONFIG with(rowlock) set CONFIG_VALUE=?,CONFIG_TYPE=?,CONFIG_DESC=?,CONFIG_REQ=?,LASTEDITBY=?,LASTEDITDT=? where CONFIG_NAME=?", params);
        MyLogger.sysLogger.info(username + " update configs: \n" + info);
        initConfig();
        return result;
    }

    public static void deleteConfig(String username, String config_name) throws SQLException, ClassNotFoundException {
        updateDatabase("delete from MYSWORD_CONFIG with(rowlock) where CONFIG_NAME=?", config_name);
        MyLogger.sysLogger.info(username + " delete config: " + config_name);
    }

	public static String getConfString(String name) {
		return StringUtils.trimToEmpty(props.get(name));
	}

    public static ArrayList<SystemConfBean> listConfigDetail() throws SQLException, ClassNotFoundException {
        return queryDatabase("select * from MYSWORD_CONFIG(nolock) order by CONFIG_TYPE,CONFIG_NAME asc");
    }
}
