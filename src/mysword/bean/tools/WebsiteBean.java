package mysword.bean.tools;

import java.util.List;

public class WebsiteBean {

	private String website_type;
	private String owner;
	private List<WebsiteItemBean> items;
	public String getWebsite_type() {
		return website_type;
	}
	public void setWebsite_type(String website_type) {
		this.website_type = website_type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public List<WebsiteItemBean> getItems() {
		return items;
	}
	public void setItems(List<WebsiteItemBean> items) {
		this.items = items;
	}
}
