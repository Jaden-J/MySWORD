package mysword.utils;

import mysword.system.logger.MyLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@SuppressWarnings("unused")
public class EmailMessage extends MimeMessage {

	public EmailMessage(Folder arg0, InputStream arg1, int arg2) throws MessagingException {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public EmailMessage(Folder arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public EmailMessage(Folder arg0, InternetHeaders arg1, byte[] arg2, int arg3) throws MessagingException {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public EmailMessage(MimeMessage arg0) throws MessagingException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public EmailMessage(Session arg0, InputStream arg1) throws MessagingException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public EmailMessage(Session arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void setHtmlMsg(String htmlMsg, String charset) throws IOException, MessagingException {
		MimeMultipart mmp = (MimeMultipart)this.getContent();
		BodyPart htmlPart = new MimeBodyPart();
		for(int i=0; i<mmp.getCount(); i++) {
			BodyPart contentPart = mmp.getBodyPart(i);
			if(contentPart.getContentType().matches("(?i)^text/html")) {
				htmlPart = contentPart;
				mmp.removeBodyPart(htmlPart);
			}
		}
		htmlPart.setHeader("Content-Type", "text/html; charset=" + charset);
		htmlPart.setContent(htmlMsg, "text/html; charset=" + charset);
		mmp.addBodyPart(htmlPart);
	}
	
	public void addTo(InternetAddress... addr) throws MessagingException {
		this.addRecipients(RecipientType.TO, addr);
	}
	
	public void addTo(String... addr) throws MessagingException {
		for(String temp : addr) {
			this.addRecipient(RecipientType.TO, new InternetAddress(temp));
		}
	}
	
	public void setTo(String addr, String personal) throws MessagingException, UnsupportedEncodingException {
		this.setTo(new InternetAddress(addr, personal));
	}
	
	public void setTo(String... addr) throws MessagingException {
		Address[] addrArray;
		if(addr != null) {
			addrArray = new Address[0];
			for(String temp : addr) {
				addrArray = ArrayUtils.add(addrArray, new InternetAddress(temp));
			}
		} else {
			return;
		}
		this.setRecipients(RecipientType.TO, addrArray);
	}
	
	public void setTo(InternetAddress... addr) throws MessagingException {
		this.setRecipients(RecipientType.TO, addr);
	}

    public void addBodyParts(BodyPart... bpList) throws IOException, MessagingException {
        MimeMultipart mmp = (MimeMultipart)this.getContent();
        for(BodyPart bp:bpList) {
            mmp.addBodyPart(bp);
        }
    }

	public void addCc(InternetAddress... addr) throws MessagingException {
		this.addRecipients(RecipientType.CC, addr);
	}
	
	public void addCc(String... addr) throws MessagingException {
		for(String temp : addr) {
			this.addRecipient(RecipientType.CC, new InternetAddress(temp));
		}
	}
	
	public void setCc(String addr, String personal) throws MessagingException, UnsupportedEncodingException {
		this.setCc(new InternetAddress(addr, personal));
	}
	
	public void setCc(String addr) throws MessagingException {
		this.setRecipients(RecipientType.CC, addr);
	}
	
	public void setCc(String... addr) throws MessagingException {
		Address[] addrArray;
		if(addr != null) {
			addrArray = new Address[0];
			for(String temp : addr) {
				addrArray = ArrayUtils.add(addrArray, new InternetAddress(temp));
			}
		} else {
			return;
		}
		this.setRecipients(RecipientType.CC, addrArray);
	}
	
	public void setCc(InternetAddress... addr) throws MessagingException {
		this.setRecipients(RecipientType.CC, addr);
	}

	public void addBcc(InternetAddress... addr) throws MessagingException {
		this.addRecipients(RecipientType.BCC, addr);
	}
	
	public void addBcc(String... addr) throws MessagingException {
		for(String temp : addr) {
			this.addRecipient(RecipientType.BCC, new InternetAddress(temp));
		}
	}
	
	public void setBcc(String addr, String personal) throws MessagingException, UnsupportedEncodingException {
		this.setBcc(new InternetAddress(addr, personal));
	}
	
	public void setBcc(String addr) throws MessagingException {
		this.setRecipients(RecipientType.BCC, addr);
	}
	
	public void setBcc(String... addr) throws MessagingException {
		Address[] addrArray;
		if(addr != null) {
			addrArray = new Address[0];
			for(String temp : addr) {
				addrArray = ArrayUtils.add(addrArray, new InternetAddress(temp));
			}
		} else {
			return;
		}
		this.setRecipients(RecipientType.BCC, addrArray);
	}
	
	public void setBcc(InternetAddress... addr) throws MessagingException {
		this.setRecipients(RecipientType.BCC, addr);
	}
	
	public void setFrom(String addr, String personal) throws MessagingException, UnsupportedEncodingException {
		this.setFrom(new InternetAddress(addr, personal));
	}
	
	public void setFrom(String addr) throws MessagingException, UnsupportedEncodingException {
		this.setFrom(new InternetAddress(addr));
	}
	
	public void setFrom(InternetAddress addr) throws MessagingException {
		this.setFrom((Address)addr);
	}
	
	public Address[] getToArray() throws MessagingException {
		return this.getRecipients(RecipientType.TO);
	}
	
	public ArrayList<InternetAddress> getToList() throws MessagingException {
		ArrayList<InternetAddress> result = new ArrayList<InternetAddress>();
		for(Address addr : this.getRecipients(RecipientType.TO)) {
			result.add((InternetAddress)addr);
		}
		return result;
	}

	public String getToString() throws MessagingException {
		return listAddressToString(this.getRecipients(RecipientType.TO));
	}
	
	public InternetAddress getFromAddress() throws MessagingException {
		InternetAddress result = new InternetAddress();
		for(Address addr : this.getFrom()) {
			result = (InternetAddress)addr;
		}
		return result;
	}
	
	public String getFromString() throws MessagingException {
		return listAddressToString(this.getFrom());
	}
	
	public Address[] getCcArray() throws MessagingException {
		return this.getRecipients(RecipientType.CC);
	}
	
	public ArrayList<InternetAddress> getCcList() throws MessagingException {
		ArrayList<InternetAddress> result = new ArrayList<InternetAddress>();
        if(this.getRecipients(RecipientType.CC) != null) {
            for (Address addr : this.getRecipients(RecipientType.CC)) {
                result.add((InternetAddress) addr);
            }
        }
		return result;
	}
	
	public String getCcString() throws MessagingException {
		return listAddressToString(this.getRecipients(RecipientType.CC));
	}
	
	public Address[] getBccArray() throws MessagingException {
		return this.getRecipients(RecipientType.BCC);
	}
	
	public ArrayList<InternetAddress> getBccList() throws MessagingException {
		ArrayList<InternetAddress> result = new ArrayList<InternetAddress>();
        if(this.getRecipients(RecipientType.BCC) != null) {
            for (Address addr : this.getRecipients(RecipientType.BCC)) {
                result.add((InternetAddress) addr);
            }
        }
		return result;
	}
	
	public String getBccString() throws MessagingException {
		return listAddressToString(this.getRecipients(RecipientType.BCC));
	}
	
	public String getReplyToString() throws MessagingException {
		return listAddressToString(this.getReplyTo());
	}
	
	public boolean hasAddress(String type, String address) throws MessagingException {
		address = HexUtils.stringToHex(address);
		String addrStr = "";
		if("to".equals(type)) {
			addrStr = this.getToString();
		} else if("cc".equals(type)) {
			addrStr = this.getCcString();
		} else if("bcc".equals(type)) {
			addrStr = this.getBccString();
		} else if("from".equals(type)) {
			addrStr = this.getFromString();
		}
		String regex = "(?i)(^" + address + ",.*|.*," + address + ",.*|.*," + address + "$|^" + address + "$)";
        return addrStr.matches(regex);
	}
	
	public boolean hasFromAddress(String address) throws MessagingException {
        return !StringUtils.isEmpty(address) && hasAddress("from", address);
    }
	
	public boolean hasStrictFromAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
            return false;
        }
		return hasAddress("from", address);
	}
	
	public boolean hasToAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return true;
		}
		return hasAddress("to", address);
	}
	
	public boolean hasStrictToAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return false;
		}
		return hasAddress("to", address);
	}
	
	public boolean hasCcAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return true;
		}
		return hasAddress("cc", address);
	}
	
	public boolean hasStrictCcAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return false;
		}
		return hasAddress("cc", address);
	}
	
	public boolean hasBccAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return true;
		}
		return hasAddress("bcc", address);
	}
	
	public boolean hasStrictBccAddress(String address) throws MessagingException {
		if(StringUtils.isEmpty(address)) {
			return false;
		}
		return hasAddress("bcc", address);
	}
	
	public boolean containSubject(String str) throws MessagingException {
		String subject = StringUtils.trimToEmpty(this.getSubject());
		return subject.matches(".*" + HexUtils.stringToHex(str) + ".*");
	}
	
	public boolean containSubjectIgnoreCase(String str) throws MessagingException {
		String subject = StringUtils.trimToEmpty(this.getSubject());
		return subject.matches("(?i).*" + HexUtils.stringToHex(str) + ".*");
	}
	
	public boolean dateBetween(Date start, Date end) throws MessagingException {
		if(this.getReceivedDate() == null) {
			return true;
		}
		if(start == null && end == null) {
			return true;
		} else if (start == null && end != null) {
			return end.after(this.getReceivedDate());
		} else if (start != null && end == null) {
			return start.before(this.getReceivedDate());
		} else {
			return start.before(this.getReceivedDate()) && end.after(this.getReceivedDate());
		}
	}
	
	public static String listAddressToString(Address... addrList) {
		StringBuffer str = new StringBuffer();
		for(Address temp : addrList) {
			InternetAddress addr = (InternetAddress)temp;
			str.append(addr.getAddress() + ",");
		}
		return StringUtils.removeEnd(str.toString(), ",");
	}
	
	public static String listAddressToString(String delimiter, Address... addrList) {
		StringBuffer str = new StringBuffer();
		for(Address temp : addrList) {
			InternetAddress addr = (InternetAddress)temp;
			str.append(addr.getAddress() + delimiter);
		}
		return StringUtils.removeEnd(str.toString(), delimiter);
	}
	
	public static String listAddressToString(InternetAddress... addrList) {
		StringBuffer str = new StringBuffer();
		for(InternetAddress addr : addrList) {
			str.append(addr.getAddress() + ",");
		}
		return StringUtils.removeEnd(str.toString(), ",");
	}
	
	public static String listAddressToString(String delimiter, InternetAddress... addrList) {
		StringBuffer str = new StringBuffer();
		for(InternetAddress addr : addrList) {
			str.append(addr.getAddress() + delimiter);
		}
		return StringUtils.removeEnd(str.toString(), delimiter);
	}
	
	public static String listAddressToString(List<InternetAddress> addrList) {
		StringBuffer str = new StringBuffer();
		for(InternetAddress addr : addrList) {
			str.append(addr.getAddress() + ",");
		}
		return StringUtils.removeEnd(str.toString(), ",");
	}
	
	public static String listAddressToString(List<InternetAddress> addrList, String delimiter) {
		StringBuffer str = new StringBuffer();
		for(InternetAddress addr : addrList) {
			str.append(addr.getAddress() + delimiter);
		}
		return StringUtils.removeEnd(str.toString(), delimiter);
	}
	
	public boolean writeTo(String filename) {
		try {
			MimeMessageUtils.writeMimeMessage(this, new File(filename));
			return true;
		} catch(Exception e) {
			MyLogger.sysLogger.error(MyLogger.getErrorString(e));
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean writeTo(File f) {
		try {
			MimeMessageUtils.writeMimeMessage(this, f);
			return true;
		} catch(Exception e) {
			MyLogger.sysLogger.error(MyLogger.getErrorString(e));
			e.printStackTrace();
			return false;
		}
	}
	
	public static Collection<InternetAddress> arrayToAddress(Address[] addr) {
		HashSet<InternetAddress> hs = new HashSet<InternetAddress>();
		if(addr != null) {
			CollectionUtils.addAll(hs, addr);
		}
		return hs;
	}
}
