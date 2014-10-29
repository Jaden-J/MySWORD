package mysword.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by hyao on 7/5/2014.
 */
public class MACUtils {
    public static String getMacAddressByIP(String ip) throws IOException {
        if("127.0.0.1".equals(ip)) {
            return "SERVERMAC";
        }
        String str = "";
        String macAddress = "";
        try {
            Process pp = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(
                                str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return macAddress;
    }

//    public static String getMacAddressByIP(String ipAddress) throws UnknownHostException, SocketException {
//        InetAddress ia;
//        if("127.0.0.1".equals(ipAddress)) {
//            return "SERVERMAC";
//        } else {
//            ia = InetAddress.getByName(ipAddress);
//        }
//        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
//        StringBuffer sb = new StringBuffer();
//        for(int i=0;i<mac.length;i++){
//            if(i!=0){
//                sb.append("-");
//            }
//            String s = Integer.toHexString(mac[i] & 0xFF);
//            sb.append(s.length()==1?0+s:s);
//        }
//        return sb.toString().toUpperCase();
//    }

    public static String getMacAddressByName(String hostname) throws IOException {
        return getMacAddressByIP(InetAddress.getByName(hostname).getHostAddress());
    }
}
