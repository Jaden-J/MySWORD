package mysword.bean.system;

import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EmailBean {

	private List<InternetAddress> to = new ArrayList<InternetAddress>();
	private InternetAddress from = new InternetAddress();
	private List<InternetAddress> cc = new ArrayList<InternetAddress>();
	private List<InternetAddress> bcc = new ArrayList<InternetAddress>();
    private String messageId;
    private String[] references = new String[0];
	private String subject;
	private Date receiveDate;
	private Date sendDate;
	private String contentType;
	private String body;
	private String filename;
	private HashMap<String, InputStream> attachments = new HashMap<String, InputStream>();
    private HashMap<String, String> headers = new HashMap<String, String>();

    public List<InternetAddress> getTo() {
        return to;
    }

    public void setTo(List<InternetAddress> to) {
        this.to = to;
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public List<InternetAddress> getCc() {
        return cc;
    }

    public void setCc(List<InternetAddress> cc) {
        this.cc = cc;
    }

    public List<InternetAddress> getBcc() {
        return bcc;
    }

    public void setBcc(List<InternetAddress> bcc) {
        this.bcc = bcc;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public HashMap<String, InputStream> getAttachments() {
        return attachments;
    }

    public void setAttachments(HashMap<String, InputStream> attachments) {
        this.attachments = attachments;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
