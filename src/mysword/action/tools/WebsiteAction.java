package mysword.action.tools;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.tools.WebsiteBean;
import mysword.dao.tools.WebsiteDAO;
import org.apache.struts2.ServletActionContext;

import java.util.List;

public class WebsiteAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<WebsiteBean> websiteList;
	
	public List<WebsiteBean> getWebsiteList() {
		return websiteList;
	}
	public void setWebsiteList(List<WebsiteBean> websiteList) {
		this.websiteList = websiteList;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		websiteList = WebsiteDAO.getWebsiteDetail(ServletActionContext.getRequest().getRemoteUser());
		return "success_website";
	}

}
