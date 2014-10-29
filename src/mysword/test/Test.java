package mysword.test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mysword.bean.project.ProjectTypeBean;
import mysword.bean.project.ProjectStructureBean;
import mysword.dao.project.ProjectDAO;
import mysword.dao.tools.MapDAO;
import mysword.utils.EmailMessage;
import mysword.utils.InetAddressUtils;
import mysword.utils.MACUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.util.MimeMessageUtils;
import org.apache.commons.net.util.Base64;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;


public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        /*
		FileUtils.writeStringToFile(new File("d:/test123456123123123.txt"), "dfasdfasdfasdfasdfasf", "UTF-8");
		FileOutputStream fos = new FileOutputStream("d:\\output.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write("asdfasdfasdfsfs"); 
        osw.flush();
        */
//        FileUtils.writeStringToFile(new File("D://test.txt"), "我是中国人", "unicode", false);
//        System.out.println(getMacAddressIP("10.213.21.104"));
//        KnowledgeSharingBean result = KnowledgeSharingDAO.getKnowledgeSharing("1", false);
//        System.out.println(result.getSubject());
//        SystemSchedule.runSchedule();
//        mail();
//        System.out.println(EmailUtils.getTextBodyFromFile("d:\\email\\gabriele.klar@dbschenker.com_2013-12-06 214502.eml"));
//        System.out.println(EmailUtils.getHtmlBodyFromFile("d:\\email\\gabriele.klar@dbschenker.com_2013-12-06 214502.eml"));
//        ArrayList<KnowledgeSharingBean> result =  KnowledgeSharingDAO.getKnowledgeSharing(null, null, null, null, null, null, "this is", -2, -2, true);
//
//        for(KnowledgeSharingBean ksb : result) {
//            System.out.println("=============================");
//            System.out.println("ShareID: " + ksb.getShare_Id());
//            System.out.println("Subject: " + ksb.getSubject());
//            System.out.println("Filename: " + ksb.getFilename());
//            System.out.println("Filename: " + ksb.getContent());
//            System.out.println("=============================");
//        }
//        EmailUtils.getFinalBodyFromFile("D:\\email\\halberd.yao@dbschenker.com_2014-01-14 104430.eml");
//        StringBuffer sb = new StringBuffer();
//        J2SSH.command("ls", sb, "xibmtdev.dc.signintra.com", 22, "customer", "Hae2thei");
//        System.out.println(sb.toString());
//        testProjectType2();
//        System.out.println(NumberUtils.toInt("asdfasdf"));
//        String str = new String(Base64.decodeBase64(""));
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.set(Calendar.YEAR, 1899);
//        cal.set(Calendar.MONTH, 11);
//        cal.set(Calendar.DATE, 30);
//        cal.add(Calendar.DATE, 41528);
//        System.out.println(cal.get(Calendar.YEAR));
//        System.out.println(cal.get(Calendar.MONTH));
//        System.out.println(cal.get(Calendar.DATE));
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.213.20.8:1433;databaseName=mysword", "cei", "Schenker1");
//        conn.setAutoCommit(false);
//        conn.close();
//        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
//        System.out.println(MapDAO.checkInMap("46", "E_MAP_TEST", "123123"));
//        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
//        Channel channel = null;
//        com.jcraft.jsch.Session session = null;
//        try {
//            JSch jsch = new JSch();
//            jsch.setConfig("PreferredAuthentications", "password");
//            session = jsch.getSession(username, hostname, 22);
//            session.setPassword(password);
//            java.util.Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//            session.connect(6000);
//
//            channel = session.openChannel("exec");
//            ((ChannelExec) channel).setCommand(command);
//            OutputStream outStream = channel.getOutputStream();
//            InputStream inputStream = channel.getInputStream();
//            ((ChannelExec) channel).setAgentForwarding(true);
//            channel.connect(6000);
//            for (int i = 0; i < 5; i++) {
//                Thread.sleep(1000);
//                if (inputStream.available() > 0)
//                    break;
//                i++;
//            }
//            if (inputStream.available() > 0) {
//                byte[] data = new byte[inputStream.available()];
//                inputStream.read(data);
//                logs.append(new String(data));
//            }
//            outStream.close();
//            inputStream.close();
//            if (channel != null) {
//                channel.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//            return true;
//        } catch (JSchException e) {
//            if (channel != null) {
//                channel.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//            logs.append(e.toString() + "\n");
//            throw e;
//        } catch (InterruptedException e) {
//            if (channel != null) {
//                channel.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//            logs.append(e.toString() + "\n");
//            throw e;
//        } catch (IOException e) {
//            if (channel != null) {
//                channel.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//            logs.append(e.toString() + "\n");
//            throw e;
//        }
//        InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象
//        byte[] ba = new byte[]{(byte)127, (byte)0, (byte)0, (byte)1};
//        if(ba[0] == (byte)127 && ba[1] == (byte)0 && ba[2] == (byte)0 && ba[3] == (byte)1) {
//            System.out.println("Local");
//        }
//        System.out.println(new String(ba));
//        ia = InetAddress.getByAddress(new byte[]{(byte)127, (byte)0, (byte)0, (byte)1});
//        System.out.println("MAC ......... "+getMACAddress(ia));
        System.out.println(MACUtils.getMacAddressByName("127.0.0.1"));
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }

    //获取MAC地址的方法
    private static String getMACAddress(InetAddress ia)throws Exception{
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();

        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }

        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    public static void testProjectType2() throws IOException {
        ProjectTypeBean pdb = new ProjectTypeBean();
        pdb.setRegions(new String[]{"CORPORATE", "APAC", "EAST"});
        pdb.setTypes(new String[]{"Planned", "Fastlane", "Support"});
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectStructureBean.class);
        ProjectDAO.saveProjectTypes(pdb);
    }

    public static void testProjectType() throws IOException {
        ProjectStructureBean ptb = new ProjectStructureBean();
        ptb.setRegion("CORPORATE");
        ptb.setProjectTypeId("1");
        ptb.setTypes(new String[]{"Planned", "b", "c"});

        ProjectStructureBean ptb1 = new ProjectStructureBean();
        ptb1.setRegion("B");
        ptb1.setProjectTypeId("2");
        ptb1.setTypes(new String[]{"a", "b", "c"});
        XStream xs = new XStream(new DomDriver());
        xs.alias("ProjectType", ProjectStructureBean.class);
//        FileUtils.writeStringToFile(new File("d:/test.xml"), xs.toXML(ptb));
        ProjectDAO.saveProjectStructure(new ProjectStructureBean[]{ptb, ptb1});
    }

    public static void testProjectType1() throws IOException {
        ProjectStructureBean[] ptb = ProjectDAO.getProejctStructure();
        System.out.println(ptb.length);
    }

    public static void testXStream() throws IOException {
//        XStream xs = new XStream(new DomDriver());
//        String configFile = "";
//        xs.alias("project", ProjectTypeBean.class);
//        configFile = "d:/test.xml";
//        ProjectTypeBean ptb = new ProjectTypeBean();
//        ptb.setRegion("123");
//        ptb.setSequence("1");
//        ptb.setType(new String[]{"a", "b", "c"});
//        ProjectTypeBean ptb1 = new ProjectTypeBean();
//        ptb1.setRegion("123");
//        ptb1.setSequence("1");
//        ptb1.setType(new String[]{"a", "b", "c"});
//        ArrayList<ProjectTypeBean> list = new ArrayList<ProjectTypeBean>();
//        list.add(ptb);
//        list.add(ptb1);
//        FileUtils.writeStringToFile(new File(configFile), xs.toXML(list));
    }

    public static void mail() throws  Exception{
//        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File("d:\\email\\gabriele.klar@dbschenker.com_2013-12-06 214502.eml"))));
//        getEmailBody(em);
//        System.out.println("ContentID: " + em.getContentID());
//        System.out.println("Send Date: " + em.getSentDate());
//        System.out.println("Content: " + em.getContent());
//        Object content = em.getContent();
//        if(content instanceof MimeMultipart) {
//            MimeMultipart con = (MimeMultipart)content;
//            for(int i=0; i<con.getCount(); i++) {
//                System.out.println("====================================");
//                System.out.println("Content-Type: " + con.getBodyPart(i).getContentType());
//                System.out.println(con.getBodyPart(i).getContent().toString());
//                System.out.println("====================================");
//            }
//        }
//        Enumeration e = em.getAllHeaders();
//        while(e.hasMoreElements()) {
//            Header h = (Header)e.nextElement();
//            System.out.println(h.getName());
//        }
    }

    public static String getEmailBody(EmailMessage em) throws IOException, MessagingException {
        Object content = em.getContent();
        if(content instanceof MimeMultipart) {
            MimeMultipart con = (MimeMultipart)content;
            for(int i=0; i<con.getCount(); i++) {
                if(StringUtils.contains(con.getBodyPart(i).getContentType(), "text/plain"))
                System.out.println(con.getBodyPart(i).getContent().toString());
            }
        }
        return "";
    }

    public static String getMacAddressIP(String remotePcIP) {
        String str = "";
        String macAddress = "";
        try {
            Process pp = Runtime.getRuntime().exec("nbtstat -A " + remotePcIP);
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

    public static void test1() throws InterruptedException {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("test1: begin " + time);
//        Thread.sleep(50000);
        System.out.println("test1: end " + time);
    }

    public static void test2() throws InterruptedException {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("test2: begin " + time);
//        Thread.sleep(50000);
        System.out.println("test2: end " + time);
    }
}
