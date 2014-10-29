package mysword.utils;


import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hyao on 7/31/2014.
 */
public class InetAddressUtils {

    public static String getHostname(String ip) throws UnknownHostException {
        if("127.0.0.1".equals(ip)) {
            return InetAddress.getLocalHost().getHostName();
        }
        String[] ipList = StringUtils.split(ip, ".");
        if(ipList.length == 4) {
            String hostname = InetAddress.getByName(ip).getHostName();
            if(StringUtils.equals(ip, hostname)) {
                return null;
            } else {
                return hostname;
            }
        } else {
            return null;
        }
    }
}
