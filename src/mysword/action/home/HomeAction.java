package mysword.action.home;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.home.NavigatorItemBean;
import mysword.dao.home.NavigatorDAO;

import java.util.ArrayList;

public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception { return SUCCESS; }

    public String home() throws Exception { return "success_home"; }

    public String about() throws Exception { return "success_about"; }
}
