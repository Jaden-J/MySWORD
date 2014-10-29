package mysword.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import mysword.bean.system.SystemAuthBean;
import mysword.dao.knowledgesharing.KnowledgeSharingDAO;
import mysword.system.SystemAuthentication;
import mysword.system.logger.MyLogger;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.SQLException;

public class AuthInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        ServletActionContextUtils.setSessionAttribute("mysword_path", ServletActionContextUtils.getContextPath());
        String result;
        if(ServletActionContextUtils.getSessionAttribute("mysword_username") == null) {
            System.out.println(ServletActionContextUtils.getRemoteAddress());
            MyLogger.sysLogger.info(ServletActionContextUtils.getRemoteAddress()+"["+ServletActionContextUtils.getRemoteMac()+"] try to login.");
            SystemAuthBean sab = SystemAuthentication.getUserByMac(ServletActionContextUtils.getRemoteMac());
            if (sab == null || StringUtils.isEmpty(sab.getUsername())) {
                ServletActionContextUtils.setSessionAttribute("errorTitle", "Only registered user can access this web page!");
                MyLogger.sysLogger.info(ServletActionContextUtils.getRemoteAddress()+"["+ServletActionContextUtils.getRemoteMac()+"] login fail caused by wasnt registered.");
                return "permissionDenied";
            } else {
                ServletActionContextUtils.setSessionAttribute("mysword_username", sab.getUsername());
                ServletActionContextUtils.setSessionAttribute("mysword_region", sab.getRegion());
                ServletActionContextUtils.setSessionAttribute("mysword_theme", sab.getTheme());
                ServletActionContextUtils.setSessionAttribute("mysword_developer", sab.isDeveloper());
                ServletActionContextUtils.setSessionAttribute("mysword_knowledge_sharing_user", sab.isKnowledge_sharing_user());
                ServletActionContextUtils.setSessionAttribute("mysword_knowledge_sharing_admin", sab.isKnowledge_sharing_admin());
                ServletActionContextUtils.setSessionAttribute("mysword_support", sab.isSupport());
                ServletActionContextUtils.setSessionAttribute("mysword_sysadmin", sab.isSysadmin());
                MyLogger.sysLogger.info(sab.getUsername()+" login.");
            }
        }
        if(ServletActionContextUtils.getRemoteURI().matches(".*/knowledge_sharing/.*")) {
            if(!(Boolean)ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_user") && !(Boolean)ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin")) {
                ServletActionContextUtils.setRequestAttribute("errorTitle", "Only knowledge sharing user and administrator can access this web page!");
                return "permissionDenied";
            }
            if(ServletActionContextUtils.getRemoteURI().matches(".*knowledge_sharing/KnowledgeSharing!(deleteKnowledgeSharing|updateKnowledgeSharing).*")) {
                if(!(Boolean)ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin")) {
                    ServletActionContextUtils.setRequestAttribute("errorTitle", "Only knowledge sharing administrator can access this web page!");
                    return "permissionDenied";
                }
            }
        } else if(ServletActionContextUtils.getRemoteURI().matches(".*/(tools/((Test|Map)Tool|SupportInformation)|project/).*")) {
            if(!(Boolean)ServletActionContextUtils.getSessionAttribute("mysword_developer")) {
                ServletActionContextUtils.setRequestAttribute("errorTitle", "Only developer can access this web page!");
                return "permissionDenied";
            }
        } else if(ServletActionContextUtils.getRemoteURI().matches(".*/system/Profile!(showProfile(?!List)|updateProfile|myProfile).*")) {

        } else if(ServletActionContextUtils.getRemoteURI().matches(".*/system/.*")) {
            if(!(Boolean)ServletActionContextUtils.getSessionAttribute("mysword_sysadmin")) {
                ServletActionContextUtils.setRequestAttribute("errorTitle", "Only system admin can access this web page!");
                return "permissionDenied";
            }
        }

        try{
            result = invocation.invoke();
        } catch(SQLException e){
            ServletActionContextUtils.setRequestAttribute("errorTitle", "Internal Error: SQL runtime error!");
            e.printStackTrace();
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            throw e;
        } catch(IOException e){
            ServletActionContextUtils.setRequestAttribute("errorTitle", "Internal Error: file processing error!");
            e.printStackTrace();
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            throw e;
        } catch(ClassNotFoundException e){
            ServletActionContextUtils.setRequestAttribute("errorTitle", "Internal Error: system cannot find the class file!");
            e.printStackTrace();
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            throw e;
        } catch(RuntimeException e){
            ServletActionContextUtils.setRequestAttribute("errorTitle", "Internal Error: runtime error!");
            e.printStackTrace();
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            throw e;
        } catch(Exception e){
            ServletActionContextUtils.setRequestAttribute("errorTitle", "Internal Error: unknown error!");
            e.printStackTrace();
            MyLogger.sysLogger.error(MyLogger.getErrorString(e));
            throw e;
        }
        return result;
	}
}
