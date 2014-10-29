package mysword.utils;

import mysword.system.conf.SystemConf;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.util.Base64;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by hyao on 8/20/2014.
 */
public class SVNUtils {

    public static final int DEVELOPMENT=0;
    public static final int INTEGRATION=1;
    public static final int TEST=2;
    public static final int PRODUCTION=3;

    private static String getSVNUsername() {
        return new String(Base64.decodeBase64(SystemConf.getConfString("server.svn.username")));
    }

    private static String getSVNPassword() {
        return new String(Base64.decodeBase64(SystemConf.getConfString("server.svn.password")));
    }

    private static String getSVNURL() {
        return SystemConf.getConfString("server.svn.url");
    }

    public static ArrayList<ByteArrayOutputStream> downloadRevisions(Object[][] files) throws SVNException {
        ArrayList<ByteArrayOutputStream> result = new ArrayList<ByteArrayOutputStream>();
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        DAVRepositoryFactory.setup();

        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(getSVNURL()));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(getSVNUsername(), getSVNPassword());
        repository.setAuthenticationManager(authManager);
        for(Object[] file:files) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            repository.getFile(String.valueOf(file[0]), NumberUtils.toLong(file[1].toString()), new SVNProperties(), baos);
            result.add(baos);
        }
        return result;
    }

    public static HashMap<String, ArrayList<SVNLogEntry>> getMapRevisions(String mapName, int environment) throws SVNException {
        HashMap<String, ArrayList<SVNLogEntry>> result = new HashMap<String, ArrayList<SVNLogEntry>>();

        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        DAVRepositoryFactory.setup();

        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(getSVNURL()));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(getSVNUsername(), getSVNPassword());
        repository.setAuthenticationManager(authManager);
        String path="";
        int startEnv=0;
        int endEnv=0;
        if(environment == -1) {
            startEnv=0;
            endEnv=3;
        } else {
            startEnv=environment;
            endEnv=environment;
        }
        for (int i = startEnv; i <= endEnv; i++) {
            String env="";
            if(i==0) {
                path = "/prod/CORPORATE_DEVELOPMENT/MAPPING/trunk/" + mapName;
                env = "DEV";
            } else if(i==1) {
                path = "/prod/CORPORATE_DEVELOPMENT/MAPPING/tags/INT/" + mapName;
                env = "INT";
            } else if(i==2) {
                path = "/prod/CORPORATE_DEVELOPMENT/MAPPING/tags/TEST/" + mapName;
                env = "TEST";
            } else if(i==3) {
                path = "/prod/CORPORATE_DEVELOPMENT/MAPPING/tags/PROD/" + mapName;
                env = "PROD";
            } else {
                continue;
            }
            SVNNodeKind nodeKind = repository.checkPath( path , -1 );
            if(nodeKind == SVNNodeKind.NONE) {
                result.put(env, new ArrayList<SVNLogEntry>());
                continue;
            }
            ArrayList<SVNLogEntry> logs = new ArrayList<SVNLogEntry>();
            Collection logEntries = repository.log(new String[]{path}, null, 0, -1, true, true);
            for(int j=logEntries.size()-1;j>=0;j--){
                logs.add((SVNLogEntry)CollectionUtils.get(logEntries,j));
            }
            result.put(env, logs);
        }
        return result;
    }

    public static ArrayList<SVNLogEntry> getDEVMapRevision(String mapName) throws SVNException {
        return getMapRevisions(mapName, DEVELOPMENT).get("DEV");
    }

    public static ArrayList<SVNLogEntry> getINTMapRevision(String mapName) throws SVNException {
        return getMapRevisions(mapName, INTEGRATION).get("INT");
    }

    public static ArrayList<SVNLogEntry> getTESTMapRevision(String mapName) throws SVNException {
        return getMapRevisions(mapName, TEST).get("TEST");
    }

    public static ArrayList<SVNLogEntry> getPRODMapRevision(String mapName) throws SVNException {
        return getMapRevisions(mapName, PRODUCTION).get("PROD");
    }
}
