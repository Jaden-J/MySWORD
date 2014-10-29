package mysword.bean.home;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class NavigatorBean {
	private ArrayList<NavigatorItemBean> items = new ArrayList<NavigatorItemBean>();

	public ArrayList<NavigatorItemBean> getItems() {
		return items;
	}
	public void setItems(ArrayList<NavigatorItemBean> items) {
		this.items = items;
	}
	public void addItem(NavigatorItemBean item) {
		this.items.add(item);
	}
	public NavigatorItemBean getItem(String id) {
		for(NavigatorItemBean temp:items) {
			if(StringUtils.equals(temp.getId(), id)) {
				return temp;
			}
		}
		return null;
	}
}
