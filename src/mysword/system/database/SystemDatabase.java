package mysword.system.database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mysword.bean.system.SystemDatabaseBean;
import mysword.system.conf.SystemConf;
import mysword.system.logger.MyLogger;
import mysword.utils.ConnectionImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings({ "unused" })
public class SystemDatabase {

    private static ArrayList<SystemDatabaseBean> databases = null;

    private static File getConfigFile(){
        return new File(SystemConf.CONF_HOME+"database.xml");
    }

    public static void initDatabaseConfig() throws IOException {
        ArrayList<SystemDatabaseBean> newDatabases = (ArrayList<SystemDatabaseBean>)getXStream().fromXML(FileUtils.readFileToString(getConfigFile(),"utf-8"));
        if(databases!=null) {
            for(SystemDatabaseBean nsdb : newDatabases) {
                boolean existing = false;
                for(SystemDatabaseBean sjb:databases) {
                    if (StringUtils.endsWith(sjb.getDatabaseName(), nsdb.getDatabaseName())) {
                        int currentThread = sjb.getCurrentThread();
                        sjb = nsdb;
                        sjb.setCurrentThread(currentThread);
                        existing=true;
                    }
                }
                if(!existing) {
                    databases.add(nsdb);
                }
            }
        } else {
            databases=newDatabases;
        }
        MyLogger.sysLogger.info("Initial database list successfully.");
    }

    public static void deleteDatabaseConfigs(String username, String... databaseNames) throws IOException {
        if(databaseNames==null||databaseNames.length==0){
            return;
        }
        for(int i=0; i<databaseNames.length; i++) {
            if(databases !=null){
                databases.remove(getDatabaseConfig(databaseNames[i]));
            }
        }
        MyLogger.sysLogger.info(username+" delete databases: " + StringUtils.join(databaseNames, "','"));
        saveDatabaseConfigs();
    }

    public static void addDatabaseConfigs(String username, SystemDatabaseBean... newDatabases) throws IOException {
        if(newDatabases==null||newDatabases.length==0){
            return;
        }
        ArrayList<String> databaseNames = new ArrayList<String>();
        for(SystemDatabaseBean sdb:newDatabases){
            databaseNames.add(sdb.getDatabaseName());
            sdb.setCurrentThread(0);
        }
        databases.addAll(Arrays.asList(newDatabases));
        MyLogger.sysLogger.info(username + " add databases: " + StringUtils.join(databaseNames, "','"));
        saveDatabaseConfigs();
    }

    public static void updateDatabaseConfigs(String username, SystemDatabaseBean... updateDatabases) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(updateDatabases==null||updateDatabases.length==0){
            return;
        }
        ArrayList<String> databaseNames = new ArrayList<String>();
        for(int i=0; i<updateDatabases.length; i++) {
            SystemDatabaseBean sdb = getDatabaseConfig(updateDatabases[i].getDatabaseName());
            databaseNames.add(sdb.getDatabaseName());
            int currentThread = sdb.getCurrentThread();
            updateDatabases[i].setCurrentThread(currentThread);
            databases.remove(sdb);
            databases.add((SystemDatabaseBean)BeanUtils.cloneBean(updateDatabases[i]));
        }
        MyLogger.sysLogger.info(username + " update databases: " + StringUtils.join(databaseNames, "','"));
        saveDatabaseConfigs();
    }

    private static XStream getXStream(){
        XStream xs = new XStream(new DomDriver());
        xs.alias("databases", new ArrayList<SystemDatabaseBean>().getClass());
        xs.alias("database", SystemDatabaseBean.class);
        return xs;
    }

    private static void saveDatabaseConfigs() throws IOException {
        ArrayList<SystemDatabaseBean> databaseList = getDatabaseConfigs();
        for(SystemDatabaseBean sdb:databaseList){
            sdb.setCurrentThread(0);
        }
        FileUtils.write(getConfigFile(), getXStream().toXML(databaseList), "utf-8");
//        getXStream().toXML(databaseList, new FileOutputStream(getConfigFile()));
        MyLogger.sysLogger.info("Save database list successfully.");
        initDatabaseConfig();
    }

    public static ArrayList<SystemDatabaseBean> getDatabaseConfigs() {
        return (ArrayList<SystemDatabaseBean>) databases.clone();
    }

    private static SystemDatabaseBean getDatabaseConfig(String connectionName){
        if(databases ==null){
            return null;
        }
        for(SystemDatabaseBean database: databases) {
            if(StringUtils.equals(database.getDatabaseName(),connectionName)){
                return database;
            }
        }
        return null;
    }

    public static ConnectionImpl getConnection(String connectionName) throws ClassNotFoundException, SQLException {
        SystemDatabaseBean database = getDatabaseConfig(connectionName);
        if(database == null) {
            throw new RuntimeException("Cannot find the JDBC config file.");
        }
        if(database.getCurrentThread() == database.getMaxThread()) {
            throw new SQLException("JDBC connections reach the max defined thread. Cannot get more JDBC connection.");
        }
        database.setCurrentThread(database.getCurrentThread()+1);
        Class.forName(database.getDriverClass());
        Connection conn = DriverManager.getConnection(database.getDatabaseURL(), database.getUsername(), database.getPassword());
        conn.setAutoCommit(false);
        return new ConnectionImpl(connectionName, UUID.randomUUID().toString(), conn);
    }

    public static void closeConnection(ConnectionImpl conn) throws SQLException {
        if(conn != null) {
            SystemDatabaseBean database = getDatabaseConfig(conn.getConnectionName());
            if(database!=null) {
                database.setCurrentThread(database.getCurrentThread() - 1);
            }
            DbUtils.closeQuietly(conn.getConnection());
        }
    }
    
    public static int update(Connection conn, String sql) throws SQLException {
        return new QueryRunner(true).update(conn, sql);
    }

    public static int updateDatabase(String connectionName, String sql, Object... params) throws SQLException, ClassNotFoundException {
        ConnectionImpl conn = null;
        int result=0;
        try {
            conn = getConnection(connectionName);
            result = new QueryRunner(true).update(conn.getConnection(), sql, params);
            conn.getConnection().commit();
            closeConnection(conn);
            return result;
        } catch(SQLException e){
            closeConnection(conn);
            throw e;
        } catch(ClassNotFoundException e){
            closeConnection(conn);
            throw e;
        }
    }
    
    public static int update(Connection conn, String sql, Object... params) throws SQLException {
        return new QueryRunner(true).update(conn, sql, params);
    }

    public static int batchUpdateDatabase(Connection connection, String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        return new QueryRunner(true).batch(connection, sql, params).length;
    }

    public static int batchUpdateDatabase(String connectionName, String sql, Object[][] params) throws SQLException, ClassNotFoundException {
        ConnectionImpl conn = null;
        int result = 0;
        try {
            conn = getConnection(connectionName);
            result = new QueryRunner(true).batch(conn.getConnection(), sql, params).length;
            conn.getConnection().commit();
            closeConnection(conn);
            return result;
        } catch(SQLException e) {
            closeConnection(conn);
            throw e;
        } catch(ClassNotFoundException e) {
            closeConnection(conn);
            throw e;
        }
    }
    
    public static int insert(Connection conn, String sql, Object... params) throws Exception {
        return new QueryRunner(true).update(conn, sql, params);
    }

    @SuppressWarnings({ "unchecked" })
	public static Object query(Connection conn, String sql, Class className) throws SQLException {
        return new QueryRunner(true).query(conn, sql, new BeanListHandler(className));
    }
    
    @SuppressWarnings({ "unchecked" })
	public static Object query(Connection conn, String sql, Class className, Object... params) throws SQLException {
        return new QueryRunner(true).query(conn, sql, new BeanListHandler(className), params);
    }

    @SuppressWarnings({ "unchecked" })
    public static Object queryDatabase(String connectionName, String sql, Class className, Object... params) throws SQLException, ClassNotFoundException {
        ConnectionImpl conn = null;
        Object result = null;
        try {
            conn = getConnection(connectionName);
            Connection conn1 = conn.getConnection();
            result = new QueryRunner(true).query(conn1, sql, new BeanListHandler(className), params);
            conn1.commit();
            closeConnection(conn);
            return result;
        } catch(SQLException e) {
            closeConnection(conn);
            throw e;
        } catch(ClassNotFoundException e){
            closeConnection(conn);
            throw e;
        }
    }
}
