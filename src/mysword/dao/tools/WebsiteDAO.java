package mysword.dao.tools;

import mysword.bean.tools.WebsiteBean;
import mysword.bean.tools.WebsiteItemBean;
import mysword.system.database.SystemDatabase;

import java.util.List;

public class WebsiteDAO {

	@SuppressWarnings("unchecked")
	public static List<WebsiteBean> getWebsiteDetail(String username) throws Exception {
		List<WebsiteBean> resultList = (List<WebsiteBean>)SystemDatabase.queryDatabase("SYSTEM","select WEBSITE_TYPE, OWNER from PERSONAL_WEBSITE_TYPE(nolock) where OWNER in ('"+username+"',"+"'All') order by WEBSITE_TYPE_SEQ asc", WebsiteBean.class);
		for(WebsiteBean temp_pwsb:resultList) {
			temp_pwsb.setItems(getWebsite(temp_pwsb.getOwner(), temp_pwsb.getWebsite_type()));
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WebsiteItemBean> getWebsite(String username) throws Exception {
		List<WebsiteItemBean> resultList = (List<WebsiteItemBean>)SystemDatabase.queryDatabase("SYSTEM",
				"select * from PERSONAL_WEBSITE_ITEM(nolock) where OWNER='" + username + "' order by WEBSITE_SEQ asc", WebsiteItemBean.class);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public static List<WebsiteItemBean> getWebsite(String username, String websiteType) throws Exception {
		List<WebsiteItemBean> resultList = (List<WebsiteItemBean>)SystemDatabase.queryDatabase("SYSTEM",
				"select * from PERSONAL_WEBSITE_ITEM(nolock) where OWNER='" + username + "' and WEBSITE_TYPE='" + websiteType + "' order by WEBSITE_SEQ asc", WebsiteItemBean.class);
		return resultList;
	}
}
