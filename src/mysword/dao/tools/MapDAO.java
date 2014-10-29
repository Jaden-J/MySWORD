package mysword.dao.tools;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import mysword.system.conf.SystemConf;
import mysword.system.logger.MyLogger;
import mysword.utils.J2SSH;
import mysword.utils.JSFTP;
import mysword.utils.SVNUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2014/8/10.
 */
public class MapDAO {

    private static String getServerName(String instance) {
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.app."+instance+".hostname"))){
            return SystemConf.getConfString("server.app."+instance+".hostname");
        } else {
            return SystemConf.getConfString("server.app."+instance+".ip");
        }
    }

    private static String getServerUsername(String instance) {
        String username="";
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.app."+instance+".username"))){
            username = SystemConf.getConfString("server.app."+instance+".username");
        } else {
            username = SystemConf.getConfString("server.default.username");
        }
        return new String(Base64.decodeBase64(username));
    }

    private static String getServerPassword(String instance) {
        String password="";
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.app."+instance+".password"))){
            password = SystemConf.getConfString("server.app."+instance+".password");
        } else {
            password = SystemConf.getConfString("server.default.password");
        }
        return new String(Base64.decodeBase64(password));
    }

    public static String getMap(String mapName, String instance, String username) throws InterruptedException, JSchException, IOException {
        MyLogger.sysLogger.info(username+" try to getmap "+mapName+" in "+instance);
        return J2SSH.getmapShell(new String[]{"sudo su - xib", getServerPassword(instance), "getmap "+mapName}, getServerName(instance), 22, getServerUsername(instance), getServerPassword(instance));
    }

    public static String checkInMap(String userIndex, String mapName, String description) throws InterruptedException, JSchException, IOException {
        StringBuffer logs = new StringBuffer();
        String command[] = new String[]{"sudo su - xib",getServerPassword("dev"),"chk_in_map_dev.sh",userIndex,mapName,description};
        return J2SSH.checkInMapShell(command, getServerName("dev"), 22, getServerUsername("dev"), getServerPassword("dev"));
    }

    public static String rolloutMap(String userIndex, String instance, String mapName, String versionIndex) throws InterruptedException, JSchException, IOException {
        String env="";
        if("dev".equals(instance)) {
            env="d";
        } else if("int".equals(instance)) {
            env="i";
        } else if("test".equals(instance)) {
            env="q";
        } else if("prod".equals(instance)) {
            env="p";
        } else {
            return "Cannot find the specify environment!";
        }
        String command[] = new String[]{"sudo su - xib", getServerPassword("dev"), "rollout_map.sh", userIndex, env, mapName, versionIndex};
        if("d".equals(env)) {
            command = (String[])ArrayUtils.add(command, "y");
        }
        return J2SSH.rolloutMapShell(command, getServerName("dev"), 22, getServerUsername("dev"), getServerPassword("dev"));
    }

    public static HashMap<String, ArrayList<SVNLogEntry>> getMapRevisions(String mapName) throws SVNException {
        return SVNUtils.getMapRevisions(mapName, -1);
    }

    public static ArrayList<SVNLogEntry> getMapRevisionsByEnv(String mapName, int environment) throws SVNException {
        if(environment == 0) {
            return SVNUtils.getDEVMapRevision(mapName);
        } else if(environment == 1) {
            return SVNUtils.getINTMapRevision(mapName);
        } else if(environment == 2) {
            return SVNUtils.getTESTMapRevision(mapName);
        } else if(environment == 3) {
            return SVNUtils.getPRODMapRevision(mapName);
        } else {
            return null;
        }
    }

    public static ArrayList<String> getArchiveList(String serverUsername) throws SftpException, JSchException {
        return JSFTP.list(getServerName("dev"), 22, getServerUsername("dev"), getServerPassword("dev"), "/ext/xib/local/user/"+serverUsername+"/datamapper/archive");
    }

    public static  void main(String[] args) throws InterruptedException, JSchException, IOException {
        System.out.println(rolloutMap("46", "dev", "E_MAP_TEST", "1"));
    }
}
