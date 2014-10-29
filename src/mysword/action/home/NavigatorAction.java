package mysword.action.home;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.home.NavigatorItemBean;
import mysword.dao.home.NavigatorDAO;

import java.util.ArrayList;

public class NavigatorAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private ArrayList<NavigatorItemBean> navigator = new ArrayList<NavigatorItemBean>();

	public ArrayList<NavigatorItemBean> getNavigator() {
		return navigator;
	}

	public void setNavigator(ArrayList<NavigatorItemBean> navigator) {
		this.navigator = navigator;
	}

	@Override
	public String execute() throws Exception {
		return "success_navigator";
	}

	public void navigatorDetail() throws Exception {
		navigator = NavigatorDAO.getNavigator().getItems();
	}

	public void updateNavigator() throws Exception {
		NavigatorDAO.updateNavigator(navigator.get(0));
	}

	public void addNavigator() throws Exception {
		NavigatorDAO.addNavigator(navigator.get(0));
	}
}
