package mysword.test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.trilead.ssh2.crypto.digest.MAC;
import mysword.bean.system.SystemDatabaseBean;
import mysword.utils.MACUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;


public class Test1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//        System.out.println(MACUtils.getMacAddressIP1("10.213.21.208"));
//        System.out.println(MACUtils.getMacAddressByIP("10.200.1.200"));
//        System.out.println(MACUtils.getMacAddressByIP("10.213.21.37"));
        System.out.println(getMacAddressByIP("10.213.22.35"));
//        System.out.println(MACUtils.getMacAddressByName("SSC1-W240.schenker-asia.com"));
    }

    public static String getMacAddressByIP(String ipAddress) throws UnknownHostException, SocketException {
        InetAddress ia;
        if("127.0.0.1".equals(ipAddress)) {
            return "SERVERMAC";
        } else {
            ia = InetAddress.getByName(ipAddress);
        }
        byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getByName("10.213.22.88")).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        return sb.toString().toUpperCase();
    }

}
