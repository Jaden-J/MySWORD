package mysword.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class JPOP3Utils {   
    
    @SuppressWarnings("unchecked")
	public static void parseMultipart(Multipart multipart, HashMap<String, Object> content) throws MessagingException, IOException {
        int count = multipart.getCount();
        for (int idx=0;idx<count;idx++) {
            BodyPart bodyPart = multipart.getBodyPart(idx);
            if(StringUtils.equalsIgnoreCase("attachment", bodyPart.getDisposition())) {
            	content.put("attachment-" + bodyPart.getFileName(), bodyPart.getInputStream());
            } else if(StringUtils.startsWith(bodyPart.getContentType(), "image")) {
            	InputStream bds = bodyPart.getInputStream();
            	byte[] imageByte = new byte[bds.available()];
            	bds.read(imageByte);
            	content.put(bodyPart.getContentType().substring(0, bodyPart.getContentType().indexOf(";")) + "-" + StringUtils.join(bodyPart.getHeader("Content-ID"), "").replaceAll("<|>", ""), "data:" + bodyPart.getContentType().substring(0, bodyPart.getContentType().indexOf(";")) + ";base64," + new String(Base64.encodeBase64(imageByte)));
            } else if(StringUtils.startsWith(bodyPart.getContentType(), "text/html")) {
            	content.put("body", bodyPart.getContent());
            }
            
            if(bodyPart.isMimeType("multipart/*")) {
                Multipart mpart = (Multipart)bodyPart.getContent();
                parseMultipart(mpart, content);
            }
        }
    }
    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len=is.read(bytes)) != -1 ) {
            os.write(bytes, 0, len);
        }
        if (os != null)   
            os.close();   
        if (is != null)   
            is.close();   
    }   
       
  
}  