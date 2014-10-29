package mysword.dao.tools;

import mysword.system.conf.SystemConf;
import mysword.utils.J2SSH;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

import java.util.HashMap;

public class ComsysDAO {

    private static String getServerAddress(String instance){
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".hostname"))) {
            return SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".hostname");
        } else {
            return SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".ip");
        }
    }

    private static String getServerUsername(String instance){
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".username"))) {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".username")));
        } else {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.default.username")));
        }
    }

    private static String getServerPassword(String instance){
        if(StringUtils.isNotEmpty(SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".password"))) {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.internal." + instance.toLowerCase() + ".password")));
        } else {
            return new String(Base64.decodeBase64(SystemConf.getConfString("server.default.password")));
        }
    }

	public static HashMap<String, Object> getAgreementConf(String agreementId, String environment, String comsysName) throws Exception {
		String[] command = new String[] { "sudo xib\n", getServerPassword(environment) + "\n", comsysName + "\n", "cd " + agreementId + "\n", "cat " + agreementId + "_dump.xml\n" };
        return J2SSH.exeShell(command, getServerAddress(environment), 22, getServerUsername(environment), getServerPassword(environment));
	}

	public static HashMap<String, Object> reloadAgreement(String agreementId, String environment, String simsName, int instance) throws Exception {
        return J2SSH.comsysShell("reload", agreementId, getServerAddress(environment), getServerUsername(environment), getServerPassword(environment), simsName, instance);
	}

	public static HashMap<String, Object> runAgreement(String agreementId, String environment, int instance) throws Exception {
        return J2SSH.comsysShell("run", agreementId, getServerAddress(environment), getServerUsername(environment), getServerPassword(environment), null, instance);
	}
}
