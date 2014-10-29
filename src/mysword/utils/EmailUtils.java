package mysword.utils;

import com.sun.mail.util.BASE64DecoderStream;
import mysword.bean.system.EmailBean;
import mysword.system.conf.SystemConf;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.util.MimeMessageUtils;
import org.apache.commons.net.util.Base64;
import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class EmailUtils {
	
	public static Session getDefaultSession() { return Session.getInstance(SystemConf.MAIL_PROPS); }
	
	public static EmailMessage getDefaultMimeMessage() throws MessagingException {
		EmailMessage em = new EmailMessage(EmailUtils.getDefaultSession());
		em.setContent(new MimeMultipart());
		return em;
	}
	
	public static void send(String[] to, String from, String subject, String body)
			throws Exception {
		send(to, null, null, from, subject, body, null, null);
	}
	
	public static void send(String[] to, String[] cc, String[] bcc, String from, String subject, String body)
			throws Exception {
		send(to, cc, bcc, from, subject, body, null, null);
	}

	public static void send(String[] to, String from, String subject, String body, File... files)
			throws Exception {
		send(to, null, null, from, subject, body, files, null);
	}

	public static void send(String[] to, String from, String subject, String body, InputStream... inputStreams)
			throws Exception {
		send(to, null, null, from, subject, body, null, inputStreams);
	}

	public static void send(String[] to, String cc[], String from, String subject, String body, File... files)
			throws Exception {
		send(to, cc, null, from, subject, body, files, null);
	}

	public static void send(String[] to, String cc[], String from, String subject, String body, InputStream... inputStreams)
			throws Exception {
		send(to, cc, null, from, subject, body, null, inputStreams);
	}

	public static void send(String[] to, String[] cc, String[] bcc, String from, String subject, String body, File[] files, InputStream[] inputStreams) throws Exception {
		EmailMessage em = EmailUtils.getDefaultMimeMessage();
		if (to != null) {
			for(String temp_to:to) {
				em.addTo(MimeUtility.encodeText(temp_to, "UTF-8", "B"));
			}
		}
		if (cc != null) {
			for(String temp_cc:cc) {
				em.addTo(MimeUtility.encodeText(temp_cc, "UTF-8", "B"));
			}
		}
		if (bcc != null) {
			for(String temp_bcc:bcc) {
				em.addTo(MimeUtility.encodeText(temp_bcc, "UTF-8", "B"));
			}
		}
		if (StringUtils.isNotEmpty(body)) {
			em.setText(MimeUtility.encodeText(body, "UTF-8", "B"));
		}
		if (StringUtils.isEmpty(from)) {
			em.setFrom(MimeUtility.encodeText(SystemConf.DEFAULT_FROM));
		} else {
			em.setFrom(MimeUtility.encodeText(from, "UTF-8", "B"));
		}
		em.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
		MimeMultipart multipart = new MimeMultipart("mixed");
		MimeBodyPart bodyPart = new MimeBodyPart();
		MimeMultipart bodyMulti = new MimeMultipart("related");
        MimeBodyPart textBody = new MimeBodyPart();  
        textBody.setContent(body, "text/html;charset=utf-8");  
        bodyMulti.addBodyPart(textBody);  
        bodyPart.setContent(bodyMulti);
        textBody.addHeader("Content-Transfer-Encoding", "base64");
        multipart.addBodyPart(bodyPart);
		if(files!=null) {
			for(File file : files) {
				MimeBodyPart mbp = new MimeBodyPart();
				ByteArrayDataSource bads = new ByteArrayDataSource(FileUtils.readFileToByteArray(file), "application/octet-stream");
				mbp.setDataHandler(new DataHandler(bads));
				mbp.setFileName(MimeUtility.encodeText(file.getName(), "UTF-8", "B"));
				mbp.addHeader("Content-Transfer-Encoding", "base64");
				multipart.addBodyPart(mbp);
			}
		}
		if(inputStreams!=null) {
			for(InputStream is : inputStreams) {
				MimeBodyPart mbp = new MimeBodyPart();
				ByteArrayDataSource bads = new ByteArrayDataSource(is, "application/octet-stream");
				mbp.setDataHandler(new DataHandler(bads));
				mbp.setFileName(DateFormatUtils.format(new Date(), "yyyy-MM-dd_HHmmssSSS") + ".txt");
				multipart.addBodyPart(mbp);
			}
		}
		em.setContent(multipart);
		em.saveChanges();
		sendMessage(em);
	}

	public static ArrayList<EmailBean> getEmailByFrom(String from, boolean delete) throws Exception {
		return getEmail(from, null, null, null, null, null, null, null, delete);
	}

	public static ArrayList<EmailBean> getEmailBySubject(String subject, boolean delete) throws Exception {
		return getEmail(null, null, null, null, subject, null, null, null, delete);
	}

	public static ArrayList<EmailBean> getEmailByFromAndSubject(String from, String subject, boolean delete) throws Exception {
		return getEmail(from, null, null, null, subject, null, null, null, delete);
	}
	
	public static ArrayList<EmailBean> getEmail(boolean delete) throws Exception {
		return getEmail(null, null, null, null, null, null, null, null, delete);
	}

	public static ArrayList<EmailBean> getEmail(String from, String subject, Date startDate, Date endDate, boolean delete) throws Exception {
		return getEmail(from, null, null, null, subject, startDate, endDate, null, delete);
	}

	public static ArrayList<EmailBean> getEmail(String from, String to, String cc, String bcc, String subject, Date startDate, Date endDate, String backupFolder, boolean delete) throws Exception {
		Session session = Session.getDefaultInstance(SystemConf.MAIL_PROPS, new MyAuthenticator(SystemConf.POP3_USERNAME, SystemConf.POP3_PASSWORD));
		Store store = session.getStore("pop3");
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		int messageCount = folder.getMessageCount();
		ArrayList<EmailBean> emails = new ArrayList<EmailBean>();
		if (messageCount > 0) {
			Message[] messages = folder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				MimeMessage message = (MimeMessage)messages[i];
				if (StringUtils.isNotEmpty(from) && !containAddress(message.getFrom(), new InternetAddress(from))) { continue; }
                if (StringUtils.isNotEmpty(to) && !containAddress(message.getAllRecipients(),  new InternetAddress(from))) { continue; }
				if (StringUtils.isNotEmpty(cc) && !containAddress(message.getRecipients(Message.RecipientType.CC), new InternetAddress(cc))) { continue; }
				if (StringUtils.isNotEmpty(bcc) && !containAddress(message.getRecipients(Message.RecipientType.BCC), new InternetAddress(bcc))) { continue; }
				if (StringUtils.isNotEmpty(subject) && !StringUtils.containsIgnoreCase(message.getSubject(), subject)) { continue; }
				if (startDate != null && startDate.after(message.getSentDate())) { continue; }
				if (endDate != null && endDate.before(message.getSentDate())) { continue; }
				EmailBean email = new EmailBean();
                email.setMessageId(message.getMessageID());
				email.setTo(addressArrayToList(message.getRecipients(RecipientType.TO)));
				email.setFrom(((InternetAddress)(message.getFrom()[0])));
				email.setCc(addressArrayToList(message.getRecipients(RecipientType.CC)));
				email.setBcc(addressArrayToList(message.getRecipients(RecipientType.BCC)));
				email.setSubject(message.getSubject());
				email.setSendDate(message.getSentDate());
				email.setReceiveDate(message.getReceivedDate());
				email.setContentType(StringUtils.substringBefore(message.getContentType(), ";"));
                Enumeration enu = message.getAllHeaders();
                HashMap<String, String> headers = new HashMap<String, String>();
                while(enu.hasMoreElements()) {
                    Header header = (Header)enu.nextElement();
                    headers.put(header.getName(), header.getValue());
                    if(StringUtils.equals("References", header.getName())) {
                        email.setReferences(StringUtils.split(header.getValue()));
                    }
                }
                email.setHeaders(headers);
				String filename = StringUtils.replaceEach(message.getSubject(), 
								new String[]{"\\","/",":","*","?","\"","<",">","|"}, 
								new String[]{"","","","","","","","",""}) +
								DateFormatUtils.format(new Date(), "_yyyyMMddHHmmssSSS") +
						".eml";
				email.setFilename(filename);
				FileOutputStream fos = null;
                if(StringUtils.isNotEmpty(backupFolder)) {
                    if(!StringUtils.endsWithAny(backupFolder, "/", "\\")) {
                        backupFolder = backupFolder + "/";
                    }
                    fos = new FileOutputStream(backupFolder + filename);
                } else {
                    fos = new FileOutputStream(SystemConf.EMAIL_FOLDER + filename);
                }
				messages[i].writeTo(fos);
				fos.close();
				if(delete) {
					messages[i].setFlag(Flags.Flag.DELETED, true);
				}
				HashMap<String, Object> emailContent = new HashMap<String, Object>();
				Object content = messages[i].getContent();
				if (content instanceof MimeMultipart) {
					MimeMultipart multipart = (MimeMultipart) content;
					parseMultipart(multipart, emailContent);
				}
				String body = (String) emailContent.get("body");
				Set<String> keys = emailContent.keySet();
				HashMap<String, InputStream> attachments = new HashMap<String, InputStream>();
				for (String key : keys) {
					if (key.startsWith("image")) {
						emailContent.put("body", StringUtils.replace(body, "cid:"
								+ key.substring(key.indexOf("-") + 1),
								emailContent.get(key).toString()));
					}
					if (key.startsWith("attachment")) {
						attachments.put(key.substring(key.indexOf("-") + 1),
								(InputStream) emailContent.get(key));
					}
				}
				email.setBody(String.valueOf(emailContent.get("body")));
				email.setAttachments(attachments);
				emails.add(email);
			}
		}
		folder.close(true);
		store.close();
		return emails;
	}
	
	public static ArrayList<InternetAddress> addressArrayToList(Address... address) {
		ArrayList<InternetAddress> result = new ArrayList<InternetAddress>();
		if(address == null) {
			return result;
		}
		for(Address temp:address) {
			result.add((InternetAddress)temp);
		}
		return result;
	}

	public static boolean sendMessage(Message message) throws MessagingException {
        if(message == null) {
            return false;
        }
		Transport transport = null;
		boolean result = false;
		try {
			transport = EmailUtils.getDefaultSession().getTransport("smtp");
			transport.connect(SystemConf.SMTP_HOSTNAME, SystemConf.SMTP_PORT, SystemConf.SMTP_USERNAME, SystemConf.SMTP_PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			result = true;
		} catch(MessagingException e) {
			result = false;
			throw e;
		} finally{
			if(transport != null) {
				transport.close();
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean containAddress(Address[] addrList, Address addr) throws Exception {
		if(addrList == null || addr == null) {
			return false;
		}
		for(Address temp : addrList) {
			InternetAddress iAddress = (InternetAddress)temp;
			if(StringUtils.equals(iAddress.getAddress(), ((InternetAddress)addr).getAddress())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void parseMultipart(Multipart multipart, HashMap<String, Object> content) throws MessagingException, IOException {
		int count = multipart.getCount();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			if (StringUtils.equalsIgnoreCase("attachment", bodyPart
					.getDisposition())) {
				content.put("attachment-" + bodyPart.getFileName(), bodyPart
						.getInputStream());
			} else if (StringUtils.startsWith(bodyPart.getContentType(),
					"image")) {
				InputStream bds = bodyPart.getInputStream();
				byte[] imageByte = new byte[bds.available()];
				bds.read(imageByte);
				content.put(StringUtils.substringBefore(bodyPart.getContentType(), ";")
						+ "-"
						+ StringUtils
								.join(bodyPart.getHeader("Content-ID"), "")
								.replaceAll("<|>", ""), "data:"
						+ StringUtils.substringBefore(bodyPart.getContentType(), ";")
						+ ";base64,"
						+ new String(Base64.encodeBase64(imageByte)));
			} else if (StringUtils.startsWith(bodyPart.getContentType(),
					"text/html")) {
				content.put("body", bodyPart.getContent());
			}
			if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(mpart, content);
			}
		}
	}

    public static String getEmailBody(EmailMessage em) throws IOException, MessagingException {
        String textBody = "";
        String htmlBody = "";
        Object content = em.getContent();
        if(content instanceof MimeMultipart) {
            MimeMultipart con = (MimeMultipart)content;
            for(int i=0; i<con.getCount(); i++) {
                if(StringUtils.contains(StringUtils.lowerCase(con.getBodyPart(i).getContentType()), "text/plain")) {
                    textBody = con.getBodyPart(i).getContent().toString();
                }
                if(StringUtils.contains(StringUtils.lowerCase(con.getBodyPart(i).getContentType()), "text/html")) {
                    htmlBody = con.getBodyPart(i).getContent().toString();
                }
            }
        }
        if(StringUtils.isNotEmpty(textBody)) {
            return textBody;
        } else {
            return htmlBody;
        }
    }

    public static String getBodyFromFile(String filename, boolean htmlFirst) throws IOException, MessagingException {
        String textBody = "";
        String htmlBody = "";
        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File(filename))));
        Object content = em.getContent();
        if(content instanceof MimeMultipart) {
            ArrayList<BodyPart> bodyParts = parseMultiPart((MimeMultipart)content);
            for(BodyPart bp:bodyParts) {
                if(StringUtils.contains(StringUtils.lowerCase(bp.getContentType()), "text/plain")) {
                    textBody = bp.getContent().toString();
                }
                if(StringUtils.contains(StringUtils.lowerCase(bp.getContentType()), "text/html")) {
                    htmlBody = bp.getContent().toString();
                }
            }
        } else if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/plain") || StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/html")) {
            return em.getContent().toString();
        }
        if(htmlFirst) {
            if(StringUtils.isNotEmpty(htmlBody)) {
                return htmlBody;
            } else {
                return textBody;
            }
        } else {
            if(StringUtils.isNotEmpty(textBody)) {
                return textBody;
            } else {
                return htmlBody;
            }
        }
    }

    public static EmailMessage getEmailMessageFromFile(String filename) throws FileNotFoundException, MessagingException {
        return new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File(filename))));
    }

    public static String getFinalBodyFromFile(String filename) throws IOException, MessagingException {
        String textBody = "";
        String htmlBody = "";
        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File(filename))));
        Object content = em.getContent();
        if(content instanceof MimeMultipart) {
            MimeMultipart con = (MimeMultipart)content;
            HashMap<String, BodyPart> bodies = pharseMultipart(con);
            if(bodies.containsKey("text/plain")) {
                textBody = bodies.get("text/plain").getContent().toString();
            }
            if(bodies.containsKey("text/html")) {
                htmlBody = bodies.get("text/html").getContent().toString();
            }
            if(StringUtils.isNotEmpty(htmlBody)) {
                for(String key : bodies.keySet()) {
                    BodyPart bp = bodies.get(key);
                    if(bp.isMimeType("image/*")) {
                        BASE64DecoderStream stream = (BASE64DecoderStream)bp.getContent();
                        byte[] bytes = IOUtils.toByteArray(stream);
                        BASE64Encoder encoder = new BASE64Encoder();
                        String imageStr = "data:" + StringUtils.split(bp.getContentType(), ";")[0] + ";base64," + encoder.encode(bytes);
                        String contentId = StringUtils.removeEnd(StringUtils.removeStart(StringUtils.join(bp.getHeader("Content-ID")), "<"), ">");
                        htmlBody = StringUtils.replace(htmlBody, "cid:" + contentId, imageStr);
                    }
                }
            }
        } else if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/plain") || StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/html")) {
            return em.getContent().toString();
        }
        if(StringUtils.isNotEmpty(htmlBody)) {
            return htmlBody;
        } else {
            return textBody;
        }
    }

    public static HashMap<String, BodyPart> pharseMultipart(Multipart mp) throws MessagingException, IOException {
        HashMap<String, BodyPart> result = new HashMap<String, BodyPart>();
        for(int i=0; i<mp.getCount(); i++) {
            BodyPart bp = mp.getBodyPart(i);
            if(bp.isMimeType("multipart/*")) {
                result.putAll(pharseMultipart((Multipart) bp.getContent()));
            } else if(bp.isMimeType("text/*")) {
                result.put(StringUtils.lowerCase(StringUtils.split(bp.getContentType(), ";")[0]), bp);
            } else {
                String key = UUID.randomUUID().toString();
                if(bp.getHeader("Content-ID") != null) {
                    key = bp.getHeader("Content-ID")[0];
                }
                result.put(key, bp);
            }
        }
        return result;
    }

    public static String getTextBodyFromFile(String filename) throws IOException, MessagingException {
        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File(filename))));
        Object content = em.getContent();
        if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "multipart")) {
            ArrayList<BodyPart> bodyParts = parseMultiPart((MimeMultipart)content);
            for(BodyPart bp:bodyParts) {
                if(StringUtils.contains(StringUtils.lowerCase(bp.getContentType()), "text/plain")) {
                    return bp.getContent().toString();
                }
            }
        } else if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/plain")) {
            return content.toString();
        }
        return "";
    }

    public static String getHtmlBodyFromFile(String filename) throws IOException, MessagingException {
        EmailMessage em = new EmailMessage(MimeMessageUtils.createMimeMessage(Session.getInstance(System.getProperties()), new FileInputStream(new File(filename))));
        Object content = em.getContent();
        if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "multipart")) {
            ArrayList<BodyPart> bodyParts = parseMultiPart((MimeMultipart)content);
            for(BodyPart bp:bodyParts) {
                if(StringUtils.contains(StringUtils.lowerCase(bp.getContentType()), "text/html")) {
                    return bp.getContent().toString();
                }
            }
        } else if(StringUtils.contains(StringUtils.lowerCase(em.getContentType()), "text/html")) {
            return content.toString();
        }
        return "";
    }

    public static ArrayList<BodyPart> parseMultiPart(Multipart mp) throws MessagingException, IOException {
        ArrayList<BodyPart> result = new ArrayList<BodyPart>();
        for(int i=0; i<mp.getCount(); i++){
            BodyPart bp = mp.getBodyPart(i);
            Object contentPart = bp.getContent();
            if(contentPart instanceof Multipart) {
                result.addAll(parseMultiPart((Multipart)contentPart));
            } else {
                result.add((BodyPart)bp);
            }
        }
        return result;
    }

}
