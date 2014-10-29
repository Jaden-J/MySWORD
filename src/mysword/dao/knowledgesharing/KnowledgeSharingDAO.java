package mysword.dao.knowledgesharing;

import mysword.bean.knowledgesharing.KnowledgeSharingBean;
import mysword.bean.system.EmailBean;
import mysword.bean.system.SystemAuthBean;
import mysword.system.SystemAuthentication;
import mysword.system.conf.SystemConf;
import mysword.system.database.SystemDatabase;
import mysword.system.logger.MyLogger;
import mysword.utils.EmailMessage;
import mysword.utils.EmailUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.util.MimeMessageUtils;

import javax.activation.DataHandler;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("unused")
public class KnowledgeSharingDAO {

    public static KnowledgeSharingBean approve(String requester, String share_Id) throws Exception {
        approveAll(requester, share_Id);
        return getKnowledgeSharing(share_Id, false);
    }

    public static void approveAll(String requester, String... share_Id) throws Exception {
        if (share_Id == null || share_Id.length < 1) {
            return;
        }
        String[][] params = new String[share_Id.length][3];
        for (int i = 0; i < share_Id.length; i++) {
            params[i][0] = requester;
            params[i][1] = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            params[i][2] = share_Id[i];
        }
        batchUpdateDatabase("update KNOWLEDGE_SHARING with(rowlock) set APPROVAL='Y', REPLY_FLAG='N', DELIVERY_FLAG='N', APPROVED_BY=?, APPROVED_DATE=? where SHARE_ID=?", params);
        sendApprovalEmail();
        sendDeliveryEmail();
        MyLogger.sysLogger.info(requester + " approve KnowledgeSharing: \n" + StringUtils.join(share_Id, "\n"));
    }

    public static void sendApprovalEmail() throws SQLException, ClassNotFoundException {
        ArrayList<KnowledgeSharingBean> approvalList = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='Y' and REPLY_FLAG='N'");
        for (KnowledgeSharingBean ksb : approvalList) {
            try {
                String body_template = FileUtils.readFileToString(new File(SystemConf.CONF_HOME + "KnowledgeSharingApproval.html"));
                body_template = StringUtils.replace(body_template, "@hostname", SystemConf.SERVER_NAME);
                body_template = StringUtils.replace(body_template, "@port", String.valueOf(SystemConf.SERVER_PORT));
                body_template = StringUtils.replace(body_template, "@context_path", SystemConf.CONTEXT_PATH);
                body_template = StringUtils.replace(body_template, "@user", ksb.getApproved_By());
                String shareIdLink = ksb.getShare_Id() + "&nbsp;<a href='http://"+SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT +
                        "/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!showKnowledgeSharing?share_Id=" + ksb
                        .getShare_Id() + "&filename=" + ksb.getFilename() + "'>(view)</a>";
                body_template = StringUtils.replace(body_template, "@shareId", shareIdLink);
                body_template = StringUtils.replace(body_template, "@subject", ksb.getSubject());
                body_template = StringUtils.replace(body_template, "@postDate", ksb.getPost_Date());
                body_template = StringUtils.replace(body_template, "@status", "Approved");
                EmailUtils.send(new String[]{ksb.getFrom_Address()}, SystemConf.DEFAULT_FROM, "Approval: " + StringUtils.trimToEmpty(ksb.getSubject()), body_template);
                updateDatabase("update KNOWLEDGE_SHARING set REPLY_FLAG='Y' where SHARE_ID=?", ksb.getShare_Id());
            } catch (Exception e) {
                MyLogger.sysLogger.error(MyLogger.getErrorString(e));
                e.printStackTrace();
            }
        }
    }

    public static void sendDeliveryEmail() throws SQLException, ClassNotFoundException {
        ArrayList<KnowledgeSharingBean> approvalList = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='Y' and DELIVERY_FLAG='N' ");
        for (KnowledgeSharingBean ksb : approvalList) {
            try {
                forwardEmailById(ksb.getShare_Id(), StringUtils.split(SystemConf.KNOWLEDGE_SHARING_GROUP, ","));
                updateDatabase("update KNOWLEDGE_SHARING with(rowlock) set DELIVERY_FLAG='Y' where SHARE_ID=?", ksb.getShare_Id());
            } catch (Exception e) {
                MyLogger.sysLogger.error(MyLogger.getErrorString(e));
                e.printStackTrace();
            }
        }
    }

    public static void delete(String requester, String share_Id) throws Exception {
        deleteAll(requester, share_Id);
    }

    public static void deleteAll(String requester, String... share_Id) throws Exception {
        if (share_Id == null || share_Id.length < 1) {
            return;
        }
        String[][] params = new String[share_Id.length][3];
        for (int i = 0; i < share_Id.length; i++) {
            params[i][0] = requester;
            params[i][1] = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            params[i][2] = share_Id[i];
        }
        batchUpdateDatabase("update KNOWLEDGE_SHARING with(rowlock) set APPROVAL='D', APPROVED_BY=?, APPROVED_DATE=?, REPLY_FLAG='N' where SHARE_ID=?", params);
        MyLogger.sysLogger.info(requester + " delete KnowledgeSharing: \n" + StringUtils.join(share_Id, "\n"));
    }

    public static void sendDeletionEmail() throws SQLException, ClassNotFoundException {
        ArrayList<KnowledgeSharingBean> deletionList = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='D' and REPLY_FLAG='N'");
        for (KnowledgeSharingBean ksb : deletionList) {
            try {
                String body_template = FileUtils.readFileToString(new File(SystemConf.CONF_HOME + "KnowledgeSharingDeletion.html"));
                body_template = StringUtils.replace(body_template, "@hostname", SystemConf.SERVER_NAME);
                body_template = StringUtils.replace(body_template, "@port", String.valueOf(SystemConf.SERVER_PORT));
                body_template = StringUtils.replace(body_template, "@context_path", SystemConf.CONTEXT_PATH);
                body_template = StringUtils.replace(body_template, "@user", ksb.getApproved_By());
                body_template = StringUtils.replace(body_template, "@shareId", ksb.getShare_Id());
                body_template = StringUtils.replace(body_template, "@subject", ksb.getSubject());
                body_template = StringUtils.replace(body_template, "@postDate", ksb.getPost_Date());
                body_template = StringUtils.replace(body_template, "@status", "Deleted");
                EmailUtils.send(new String[]{ksb.getFrom_Address()}, SystemConf.DEFAULT_FROM, "Deletion: " + StringUtils.trimToEmpty(ksb.getSubject()), body_template);
                updateDatabase("delete from KNOWLEDGE_SHARING with(rowlock) where SHARE_ID=?", ksb.getShare_Id());
            } catch (Exception e) {
                MyLogger.sysLogger.error(MyLogger.getErrorString(e));
                e.printStackTrace();
            }
        }
    }

    public static String formatAddress(List<InternetAddress> addressList) {
        if (addressList == null) {
            return "";
        }
        StringBuilder addressStr = new StringBuilder("");
        for (InternetAddress address : addressList) {
            addressStr.append(address.getAddress());
            addressStr.append(",");
        }
        return StringUtils.removeEnd(addressStr.toString(), ",");
    }

    public static String formatAddress(InternetAddress... addressList) {
        if (addressList == null) {
            return "";
        }
        StringBuilder addressStr = new StringBuilder("");
        for (InternetAddress address : addressList) {
            addressStr.append(address.getAddress());
            addressStr.append(",");
        }
        return StringUtils.removeEnd(addressStr.toString(), ",");
    }

    public static ArrayList<KnowledgeSharingBean> getKnowledgeSharing(boolean getContent) throws Exception {
        ArrayList<KnowledgeSharingBean> result = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL<>'D' order by POST_DATE desc");
        if (getContent) {
            for (KnowledgeSharingBean ksb : result) {
                ksb.setContent(EmailUtils.getFinalBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
            }
        }
        return result;
    }

    public static KnowledgeSharingBean getKnowledgeSharing(String share_Id, boolean getContent) throws ClassNotFoundException, SQLException, MessagingException, IOException {
        ArrayList<KnowledgeSharingBean> result = getKnowledgeSharing(share_Id, null, null, null, null, null, null, -2, -2, getContent);
        if (result.size() > 0) {
            if (getContent) {
                for (KnowledgeSharingBean ksb : result) {
                    ksb.setContent(EmailUtils.getFinalBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
                }
            }
            return result.get(0);
        } else {
            return null;
        }
    }

    public static InputStream getEmailMessageStream(String share_Id) throws IOException, MessagingException, SQLException, ClassNotFoundException {
        KnowledgeSharingBean ksb = getKnowledgeSharing(share_Id, false);
        EmailMessage em = EmailUtils.getEmailMessageFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename());
        em.setFrom(ksb.getFrom_Address());
        em.setTo(ksb.getTo_Address());
        em.removeHeader("Share-ID");
        em.addHeader("Share-ID", share_Id);
        em.addHeader("References", ksb.getMessageId());
        em.addHeader("In-Reply-To", ksb.getMessageId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        em.writeTo(baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static ArrayList<KnowledgeSharingBean> getKnowledgeSharing(String share_Id, String subject, String approval, String POSTER, String start_Date, String end_Date, String content, int start,
                                                                      int length, boolean getContent) throws SQLException, ClassNotFoundException, IOException, MessagingException {
        String sql = "select * from KNOWLEDGE_SHARING(nolock) where APPROVAL<>'D'";
        if(length>0) {
            sql = "select top "+length+" * from KNOWLEDGE_SHARING(nolock) where APPROVAL<>'D'";
        }
        StringBuilder condition = new StringBuilder("");
        if (StringUtils.isNotEmpty(share_Id)) {
            condition.append(" and SHARE_ID='");
            condition.append(share_Id);
            condition.append("'");
        }

        if (StringUtils.isNotEmpty(subject)) {
            condition.append(" and upper(SUBJECT) like '%");
            condition.append(subject.toUpperCase());
            condition.append("%'");
        }

        if (StringUtils.isNotEmpty(approval)) {
            condition.append(" and upper(APPROVAL) = '");
            condition.append(approval);
            condition.append("'");
        }

        if (StringUtils.isNotEmpty(POSTER)) {
            condition.append(" and POSTER='");
            condition.append(POSTER);
            condition.append("'");
        }

        if (StringUtils.isNotEmpty(start_Date)) {
            condition.append(" and POST_DATE>='");
            condition.append(start_Date);
            condition.append("'");
        }

        if (StringUtils.isNotEmpty(end_Date)) {
            condition.append(" and POST_DATE<='");
            condition.append(end_Date);
            condition.append("'");
        }
        if(StringUtils.isNotEmpty(condition.toString())) {
            sql += condition.toString();
        }
        if (start > 0 && length > 0) {
            sql += " and SHARE_ID not in( select top "+start+" SHARE_ID from KNOWLEDGE_SHARING(nolock) order by POST_DATE desc) ";
        }
        sql += " order by POST_DATE desc";
        ArrayList<KnowledgeSharingBean> temp = queryDatabase(sql);
        ArrayList<KnowledgeSharingBean> result = new ArrayList<KnowledgeSharingBean>();
        if (StringUtils.isNotEmpty(content)) {
            for (KnowledgeSharingBean ksb : temp) {
                File f = new File(getKnowledgeSharingSuccessFolder() + ksb.getFilename());
                if (!f.exists()) {
                    continue;
                }
                String bodyString = EmailUtils.getBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename(), false);
                if (!StringUtils.containsIgnoreCase(bodyString, content)) {
                    continue;
                }
                if (getContent) {
                    ksb.setContent(EmailUtils.getFinalBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
                }
                result.add(ksb);
            }
        } else {
            if (getContent) {
                for (KnowledgeSharingBean ksb : temp) {
                    ksb.setContent(EmailUtils.getFinalBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
                    result.add(ksb);
                }
            } else {
                result.addAll(temp);
            }
        }
        return result;
    }

    public static int getKnowledgeSharingCount(String share_Id, String subject, String approval, String POSTER, String start_Date, String end_Date, String content) throws Exception {
        String sql = "select SHARE_ID from KNOWLEDGE_SHARING(nolock) where APPROVAL<>'D'";
        StringBuilder condition = new StringBuilder("");
        if (StringUtils.isNotEmpty(share_Id)) {
            condition.append(" and SHARE_ID='");
            condition.append(share_Id);
            condition.append("'");
        }
        if (StringUtils.isNotEmpty(subject)) {
            condition.append(" and upper(SUBJECT) like '%");
            condition.append(subject.toUpperCase());
            condition.append("%'");
        }
        if (StringUtils.isNotEmpty(approval)) {
            condition.append(" and upper(APPROVAL) = '");
            condition.append(approval);
            condition.append("'");
        }
        if (StringUtils.isNotEmpty(POSTER)) {
            condition.append(" and POSTER='");
            condition.append(POSTER);
            condition.append("'");
        }
        if (StringUtils.isNotEmpty(start_Date)) {
            condition.append(" and POST_DATE>='");
            condition.append(start_Date);
            condition.append("'");
        }
        if (StringUtils.isNotEmpty(end_Date)) {
            condition.append(" and POST_DATE<='");
            condition.append(end_Date);
            condition.append("'");
        }
        if(StringUtils.isNotEmpty(condition.toString())) {
            sql += condition.toString();
        }
        ArrayList<KnowledgeSharingBean> temp = queryDatabase(sql);
        ArrayList<KnowledgeSharingBean> result = new ArrayList<KnowledgeSharingBean>();
        if (StringUtils.isNotEmpty(content)) {
            for (KnowledgeSharingBean ksb : temp) {
                File f = new File(getKnowledgeSharingSuccessFolder() + ksb.getFilename());
                if (!f.exists()) {
                    continue;
                }
                String bodyString = EmailUtils.getBodyFromFile(getKnowledgeSharingSuccessFolder() + ksb.getFilename(), false);
                if (!StringUtils.containsIgnoreCase(bodyString, content)) {
                    continue;
                }
                result.add(ksb);
            }
            return result.size();
        } else {
            return temp.size();
        }
    }

    public static ArrayList<KnowledgeSharingBean> getPosterList() throws Exception {
        return queryDatabase("select distinct POSTER from KNOWLEDGE_SHARING(nolock) order by POSTER asc");
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<KnowledgeSharingBean> queryDatabase(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return (ArrayList<KnowledgeSharingBean>) SystemDatabase.queryDatabase("KNOWLEDGE_SHARING", sql, KnowledgeSharingBean.class, params);
    }

    public static KnowledgeSharingBean hold(String requester, String share_Id) throws Exception {
        holdAll(requester, share_Id);
        return getKnowledgeSharing(share_Id, false);
    }

    public static void holdAll(String requester, String... share_Id) throws Exception {
        if (share_Id == null || share_Id.length < 1) {
            return;
        }
        String[][] params = new String[share_Id.length][3];
        for (int i = 0; i < share_Id.length; i++) {
            params[i][0] = requester;
            params[i][1] = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            params[i][2] = share_Id[i];
        }
        batchUpdateDatabase("update KNOWLEDGE_SHARING with(rowlock) set APPROVAL='H', REPLY_FLAG='N', APPROVED_BY=?, APPROVED_DATE=? where SHARE_ID=?", params);
        sendHoldEmail();
        MyLogger.sysLogger.info(requester + " hold KnowledgeSharing: \n" + StringUtils.join(share_Id, "\n"));
    }

    public static void sendHoldEmail() throws SQLException, ClassNotFoundException {
        ArrayList<KnowledgeSharingBean> approvalList = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='H' and REPLY_FLAG='N'");
        for (KnowledgeSharingBean ksb : approvalList) {
            try {
                String body_template = FileUtils.readFileToString(new File(SystemConf.CONF_HOME + "KnowledgeSharingHold.html"));
                body_template = StringUtils.replace(body_template, "@hostname", SystemConf.SERVER_NAME);
                body_template = StringUtils.replace(body_template, "@port", String.valueOf(SystemConf.SERVER_PORT));
                body_template = StringUtils.replace(body_template, "@context_path", SystemConf.CONTEXT_PATH);
                body_template = StringUtils.replace(body_template, "@user", ksb.getApproved_By());
                String shareIdLink = ksb.getShare_Id() + "&nbsp;<a href='http://"+SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT +
                        "/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!showKnowledgeSharing?share_Id=" + ksb
                        .getShare_Id() + "&filename=" + ksb.getFilename() + "'>(view)</a>";
                body_template = StringUtils.replace(body_template, "@shareId", shareIdLink);
                body_template = StringUtils.replace(body_template, "@subject", ksb.getSubject());
                body_template = StringUtils.replace(body_template, "@postDate", ksb.getPost_Date());
                body_template = StringUtils.replace(body_template, "@status", "Hold");
                EmailUtils.send(new String[]{ksb.getFrom_Address()}, SystemConf.DEFAULT_FROM, "Hold: " + StringUtils.trimToEmpty(ksb.getSubject()), body_template);
                updateDatabase("update KNOWLEDGE_SHARING with(rowlock) set REPLY_FLAG='Y' where SHARE_ID=?", ksb.getShare_Id());
            } catch (Exception e) {
                MyLogger.sysLogger.error(MyLogger.getErrorString(e));
                e.printStackTrace();
            }
        }
    }

    public static int addKnowledgeSharings(KnowledgeSharingBean... sharingList) throws Exception {
        if (sharingList == null) {
            return 0;
        }
        Object[][] params = new Object[sharingList.length][12];
        try {
            for (int i = 0; i < sharingList.length; i++) {
                params[i][0] = sharingList[i].getShare_Id();
                params[i][1] = sharingList[i].getFrom_Address();
                params[i][2] = sharingList[i].getTo_Address();
                params[i][3] = sharingList[i].getCc_Address();
                params[i][4] = sharingList[i].getBcc_Address();
                params[i][5] = sharingList[i].getSubject();
                params[i][6] = sharingList[i].getSendDate();
                params[i][7] = sharingList[i].getContentType();
                params[i][8] = sharingList[i].getFilename();
                params[i][9] = sharingList[i].getPoster();
                params[i][10] = sharingList[i].getPost_Date();
                params[i][11] = sharingList[i].getMessageId();
            }
            return batchUpdateDatabase("insert into KNOWLEDGE_SHARING(SHARE_ID,FROM_ADDRESS,TO_ADDRESS,CC_ADDRESS,BCC_ADDRESS,SUBJECT,SENDDATE,CONTENTTYPE,FILENAME,POSTER,POST_DATE,MESSAGEID) values(?,?,?,?,?,?,?,?,?,?,?,?)", params);
        } catch (Exception e) {
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            e.printStackTrace();
            for (Object[] param : params) {
                FileUtils.moveFile(new File(getKnowledgeSharingTempFolder() + param[8]), new File(getKnowledgeSharingErrorFolder() + param[8]));
            }
            return 0;
        }
    }

    public static int updateKnowledgeSharings(KnowledgeSharingBean... sharingList) throws Exception {
        if (sharingList == null) {
            return 0;
        }
        Object[][] params = new Object[sharingList.length][6];
        try {
            for (int i = 0; i < sharingList.length; i++) {
                params[i][0] = sharingList[i].getMessageId();
                params[i][1] = sharingList[i].getSubject();
                params[i][2] = sharingList[i].getSendDate();
                params[i][3] = sharingList[i].getContentType();
                params[i][4] = sharingList[i].getFilename();
                params[i][5] = StringUtils.join(sharingList[i].getReferences(),"','");
            }
            return batchUpdateDatabase("update KNOWLEDGE_SHARING with(rowlock) set MESSAGEID=?,APPROVAL='N',SUBJECT=?,SENDDATE=?,CONTENTTYPE=?,FILENAME=? where MESSAGEID in (?)", params);
        } catch (Exception e) {
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            e.printStackTrace();
            for (Object[] param : params) {
                FileUtils.moveFile(new File(getKnowledgeSharingTempFolder() + param[4]), new File(getKnowledgeSharingErrorFolder() + param[4]));
            }
            return 0;
        }
    }

    public static int updateDatabase(String sql, Object... params) throws Exception {
        if (StringUtils.isEmpty(sql)) {
            return 0;
        }
        return SystemDatabase.updateDatabase("KNOWLEDGE_SHARING", sql, params);
    }

    public static int batchUpdateDatabase(String sql, Object[][] params) throws Exception {
        if (StringUtils.isEmpty(sql)) {
            return 0;
        }
        return SystemDatabase.batchUpdateDatabase("KNOWLEDGE_SHARING", sql, params);
    }

//    public static void forwardEmailByFile(String filename, String... addr) throws MessagingException, IOException, SQLException, ClassNotFoundException {
//        File f = new File(getKnowledgeSharingSuccessFolder() + filename);
//        if(!f.exists()) {
//            throw new RuntimeException("The knoledge sharing email file cann't be found!");
//        }
//        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(EmailUtils.getDefaultSession(), f));
//        em.setTo(addr);
//        em.setCc("");
//        em.setBcc("");
//        em.setFrom(SystemConf.DEFAULT_FROM);
//        em.setHeader("Message-ID", "<" + UUID.randomUUID().toString() + "@dbschenker.com>");
//        em.removeHeader("Date");
//        EmailUtils.sendMessage(em);
//    }

    public static void forwardEmailById(String share_Id, String... addr) throws MessagingException, IOException, SQLException, ClassNotFoundException {
        KnowledgeSharingBean knowledge = getKnowledgeSharing(share_Id, false);
        File f = new File(getKnowledgeSharingSuccessFolder() + knowledge.getFilename());
        if(!f.exists()) {
            throw new RuntimeException("The knoledge sharing email file cann't be found!");
        }
        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(EmailUtils.getDefaultSession(), f));
        em.setTo(addr);
        em.setCc("");
        em.setBcc("");
        em.setFrom(SystemConf.DEFAULT_FROM);
        em.setHeader("Message-ID", "<" + UUID.randomUUID().toString() + "@dbschenker.com>");
        em.addHeader("References", knowledge.getMessageId());
        em.removeHeader("Date");
        EmailUtils.sendMessage(em);
    }

    public static String getKnowledgeSharingSuccessFolder() {
        String path = SystemConf.getConfString("server.knowledge.sharing.success");
        if (StringUtils.endsWithAny(path, "/", "\\\\") || StringUtils.isEmpty(path)) {
            return path;
        } else if (!StringUtils.endsWithAny(path, "/", "\\\\") && StringUtils.isNotEmpty(path)) {
            return path + "/";
        }
        return "";
    }

    public static String getKnowledgeSharingTempFolder() {
        String path = SystemConf.getConfString("server.knowledge.sharing.temp");
        if (StringUtils.endsWithAny(path, "/", "\\\\") || StringUtils.isEmpty(path)) {
            return path;
        } else if (!StringUtils.endsWithAny(path, "/", "\\\\") && StringUtils.isNotEmpty(path)) {
            return path + "/";
        }
        return "";
    }

    public static String getKnowledgeSharingErrorFolder() {
        String path = SystemConf.getConfString("server.knowledge.sharing.error");
        if (StringUtils.endsWithAny(path, "/", "\\\\") || StringUtils.isEmpty(path)) {
            return path;
        } else if (!StringUtils.endsWithAny(path, "/", "\\\\") && StringUtils.isNotEmpty(path)) {
            return path + "/";
        }
        return "";
    }

    public static void receiveEmail() throws Exception {
        ArrayList<EmailBean> emailList = EmailUtils.getEmail(null, null, null, null, "KNOWLEDGESHARING", null, null, getKnowledgeSharingTempFolder(), true);
        processKnowledgeSharingEmails(emailList);
        sendConfirmationEmail();
    }

    public static void processErrorKnowledgeSharingEmails() throws Exception {
        LinkedList<File> fileList = (LinkedList<File>)FileUtils.listFiles(new File(getKnowledgeSharingErrorFolder()), new String[]{"eml"}, false);
        ArrayList<EmailBean> emailList = new ArrayList<EmailBean>();
        for(File f:fileList) {
            EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(EmailUtils.getDefaultSession(), f));
            EmailBean email = new EmailBean();
            email.setTo(em.getToList());
            email.setFrom(em.getFromAddress());
            email.setCc(em.getCcList());
            email.setBcc(em.getBccList());
            email.setSubject(em.getSubject());
            email.setSendDate(em.getSentDate());
            email.setReceiveDate(em.getReceivedDate());
            email.setContentType(StringUtils.substringBefore(em.getContentType(), ";"));
            email.setFilename(f.getName());
            Enumeration enu = em.getAllHeaders();
            HashMap<String, String> headers = new HashMap<String, String>();
            while(enu.hasMoreElements()) {
                Header header = (Header)enu.nextElement();
                headers.put(header.getName(), header.getValue());
            }
            email.setHeaders(headers);
            emailList.add(email);
            FileUtils.moveFile(f, new File(getKnowledgeSharingTempFolder() + f.getName()));
        }
        processKnowledgeSharingEmails(emailList);
    }

    public static void processKnowledgeSharingEmails(ArrayList<EmailBean> emailList) throws Exception {
        if(emailList == null || emailList.size() == 0) {
            return;
        }
        ArrayList<KnowledgeSharingBean> newKnowledgeSharing = new ArrayList<KnowledgeSharingBean>();
        ArrayList<KnowledgeSharingBean> updateKnowledgeSharing = new ArrayList<KnowledgeSharingBean>();
        for (EmailBean eb : emailList) {
            KnowledgeSharingBean ksb = new KnowledgeSharingBean();
            ksb.setSubject(eb.getSubject());
            ksb.setShare_Id("S" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
            ksb.setMessageId(eb.getMessageId());
            ksb.setReferences(eb.getReferences());
            ksb.setSendDate(DateFormatUtils.format(eb.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
            ksb.setPost_Date(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            ksb.setFrom_Address(formatAddress(eb.getFrom()));
            ksb.setTo_Address(formatAddress(eb.getTo()));
            ksb.setCc_Address(formatAddress(eb.getCc()));
            ksb.setBcc_Address(formatAddress(eb.getBcc()));
            ksb.setContentType(eb.getContentType());
            ksb.setFilename(eb.getFilename());
            ksb.setPoster(StringUtils.substringBefore(formatAddress(eb.getFrom()), "@"));
            HashMap<String, String> headers = eb.getHeaders();
            if(isProcessedKnowledgeSharing(ksb.getReferences())) {
                updateKnowledgeSharing.add(ksb);
            } else {
                newKnowledgeSharing.add(ksb);
            }
        }
        if (newKnowledgeSharing.size() > 0) {
            if(addKnowledgeSharings(newKnowledgeSharing.toArray(new KnowledgeSharingBean[newKnowledgeSharing.size()])) > 0) {
                for (KnowledgeSharingBean ksb : newKnowledgeSharing) {
                    FileUtils.moveFile(new File(getKnowledgeSharingTempFolder() + ksb.getFilename()), new File(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
                    sendConfirmationEmail(ksb);
                }
            }
        }
        if (updateKnowledgeSharing.size() > 0) {
            if(updateKnowledgeSharings(updateKnowledgeSharing.toArray(new KnowledgeSharingBean[updateKnowledgeSharing.size()])) > 0) {
                for (KnowledgeSharingBean ksb : updateKnowledgeSharing) {
                    FileUtils.moveFile(new File(getKnowledgeSharingTempFolder() + ksb.getFilename()), new File(getKnowledgeSharingSuccessFolder() + ksb.getFilename()));
                    sendConfirmationEmail(ksb);
                }
            }
        }
    }

    public static boolean isProcessedKnowledgeSharing(String[] references) throws SQLException, ClassNotFoundException {
        if(references == null || references.length==0) {
            return false;
        }
        ArrayList<KnowledgeSharingBean> result = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where MESSAGEID in ('"+StringUtils.join(references, "','")+"')");
        return result!=null&&result.size()>0;
    }

    public static void sendConfirmationEmail(KnowledgeSharingBean... ksbList) throws Exception {
        ArrayList<KnowledgeSharingBean> confirmationList = new ArrayList<KnowledgeSharingBean>();
        if(ksbList == null || ksbList.length==0) {
            confirmationList = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='N' and REPLY_FLAG='N'");
        } else {
            confirmationList.addAll(Arrays.asList(ksbList));
        }
        for (KnowledgeSharingBean ksb : confirmationList) {
            String body_template = FileUtils.readFileToString(new File(SystemConf.CONF_HOME + "KnowledgeSharingConfirmation.html"));
            body_template = StringUtils.replace(body_template, "@hostname", SystemConf.SERVER_NAME);
            body_template = StringUtils.replace(body_template, "@port", String.valueOf(SystemConf.SERVER_PORT));
            body_template = StringUtils.replace(body_template, "@context_path", SystemConf.CONTEXT_PATH);
            String shareIdLink = ksb.getShare_Id() + "&nbsp;(<a href='http://"+SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT + "/" + SystemConf.CONTEXT_PATH +
                    "/knowledge_sharing/KnowledgeSharing!showKnowledgeSharing?share_Id="+ksb.getShare_Id()+"&filename="+ksb.getFilename()+"'>view</a>)";
            body_template = StringUtils.replace(body_template, "@shareId", shareIdLink);
            body_template = StringUtils.replace(body_template, "@subject", ksb.getSubject());
            body_template = StringUtils.replace(body_template, "@postDate", ksb.getPost_Date());
            body_template = StringUtils.replace(body_template, "@status", "Received");
            EmailUtils.send(new String[]{ksb.getFrom_Address()}, SystemConf.DEFAULT_FROM, "Conformation: " + StringUtils.trimToEmpty(ksb.getSubject()), body_template);
            updateDatabase("update KNOWLEDGE_SHARING with(rowlock) set REPLY_FLAG='Y' where SHARE_ID=?", ksb.getShare_Id());
        }
    }

    public static void sendEmailToAuditor() throws SQLException, ClassNotFoundException, IOException, MessagingException {
        StringBuilder htmlBody = new StringBuilder("");
        ArrayList<KnowledgeSharingBean> result = queryDatabase("select * from KNOWLEDGE_SHARING(nolock) where APPROVAL='N' order by POST_DATE desc");
        if (result != null && result.size() > 0) {
            for (KnowledgeSharingBean ksb : result) {
                htmlBody.append("<tr><td>");
                String showUrl = "<a href='http://"+SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT+"/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!showKnowledgeSharing?share_Id=" + ksb
                        .getShare_Id() + "&filename=" + ksb.getFilename() + "'>" + ksb.getShare_Id() + "</a>";
                htmlBody.append(showUrl);
                htmlBody.append("</td><td>");
                htmlBody.append(ksb.getSubject());
                htmlBody.append("</td><td>");
                htmlBody.append(ksb.getPoster());
                htmlBody.append("</td><td>");
                htmlBody.append(ksb.getPost_Date());
                htmlBody.append("</td><td>");
                String approveUrl = "http://"+SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT+"/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!updateKnowledgeSharing?approval=Y&web=false&share_Id=" + ksb
                        .getShare_Id();
                String holdUrl = "http://" + SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT+"/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!updateKnowledgeSharing?approval=H&web=false&share_Id=" + ksb
                        .getShare_Id();
                String deleteUrl = "http://" + SystemConf.SERVER_NAME + ":" + SystemConf.SERVER_PORT+"/"+SystemConf.CONTEXT_PATH+"/knowledge_sharing/KnowledgeSharing!deleteKnowledgeSharing?web=false&share_Id=" + ksb
                        .getShare_Id();
                String actionHTML = "<a title='Approve this item.' href='" + approveUrl + "'><img src='cid:approve-img'></a>&nbsp;" +
                        "<a href='" + holdUrl + "'><img title='Hold this item.' src='cid:hold-img'></a>&nbsp;" +
                        "<a title='Delete this item.' href='" + deleteUrl + "'><img src='cid:delete-img'></a>";
                htmlBody.append(actionHTML);
                htmlBody.append("</td></tr>");
            }
        } else {
            return;
        }
        EmailMessage em = EmailUtils.getDefaultMimeMessage();
        String[] toAddress = new String[0];
        for (SystemAuthBean sab : SystemAuthentication.getKnowledgeSharingAdmins()) {
            if (StringUtils.isNotEmpty(sab.getEmail())) {
                toAddress = (String[])ArrayUtils.add(toAddress, sab.getEmail());
            }
        }
        String body_template = FileUtils.readFileToString(new File(SystemConf.CONF_HOME + "KnowledgeSharingDisapproval.html"));
        body_template = StringUtils.replace(body_template, "@hostname", SystemConf.SERVER_NAME);
        body_template = StringUtils.replace(body_template, "@port", String.valueOf(SystemConf.SERVER_PORT));
        body_template = StringUtils.replace(body_template, "@context_path", SystemConf.CONTEXT_PATH);
        body_template = StringUtils.replace(body_template, "@knowledgeSharings", htmlBody.toString());
        em.setHtmlMsg(body_template, "utf-8");
        for(int i=0; i<3; i++) {
            String filename = "";
            String contentID = "";
            switch (i){
                case 0:
                    filename="checkmark.png";
                    contentID="approve-img";
                    break;
                case 1:
                    filename="stop.png";
                    contentID="hold-img";
                    break;
                case 2:
                    filename="delete.png";
                    contentID="delete-img";
                    break;
            }
            ByteArrayDataSource bads = new ByteArrayDataSource(FileUtils.readFileToByteArray(new File(SystemConf.ROOT_PATH + "image/knowledge_sharing/" + filename)), "application/octet-stream");
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(bads));
            mbp.setFileName(MimeUtility.encodeText(filename, "UTF-8", "B"));
            mbp.addHeader("Content-Transfer-Encoding", "base64");
            mbp.setHeader("Content-ID", contentID);
            em.addBodyParts(mbp);
        }
        em.setFrom(SystemConf.DEFAULT_FROM);
        em.setTo(toAddress);
        em.setSubject("KnowledgeSharings haven't been approved!");
        EmailUtils.sendMessage(em);
    }
}
