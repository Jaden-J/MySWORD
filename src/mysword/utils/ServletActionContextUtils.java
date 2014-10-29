package mysword.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

@SuppressWarnings("unused")
public class ServletActionContextUtils {

    public static void setRequestAttribute(String key, Object value) {
        ServletActionContext.getRequest().setAttribute(key, value);
    }

    public static Object getRequestAttribute(String key) {
        return ServletActionContext.getRequest().getAttribute(key);
    }

    public static void removeAllRequestAttributes() {
        Enumeration<String> names = ServletActionContext.getRequest().getAttributeNames();
        while(names.hasMoreElements()) {
            ServletActionContext.getRequest().removeAttribute(names.nextElement());
        }
    }

    public static void setSessionAttribute(String key, Object value) {
        ServletActionContext.getRequest().getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(String key) {
        return ServletActionContext.getRequest().getSession().getAttribute(key);
    }

    public static void removeAllSeesionAttributes() {
        Enumeration<String> names = ServletActionContext.getRequest().getSession().getAttributeNames();
        while(names.hasMoreElements()) {
            ServletActionContext.getRequest().getSession().removeAttribute(names.nextElement());
        }
    }

    public static String getContextPath() {
        return ServletActionContext.getRequest().getContextPath();
    }

    public static String getBaseURL() {
        return ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+getContextPath()+"/";
    }

    public static String getRemoteAddress() {
        return ServletActionContext.getRequest().getRemoteAddr();
    }

    public static String getRemoteMac() throws IOException {
        return MACUtils.getMacAddressByIP(ServletActionContext.getRequest().getRemoteAddr());
    }

    public static String getRemoteHostname() throws UnknownHostException {
        return InetAddressUtils.getHostname(getRemoteAddress());
    }

    public static String getRemoteUser() {
        return ServletActionContext.getRequest().getRemoteUser();
    }

    public static String getRemoteURI() {
        return ServletActionContext.getRequest().getRequestURI();
    }

    public static String getServletPath() {
        return ServletActionContext.getRequest().getServletPath();
    }

    public static String getLocalBasePath() {
        return ServletActionContext.getServletContext().getRealPath("/");
    }
}
