package mysword.system;

import mysword.system.conf.SystemConf;
import mysword.system.database.SystemDatabase;
import mysword.system.schedule.SystemSchedule;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class SystemInitial extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		super.service(arg0, arg1);
	}

	@Override
	public void init() throws ServletException {
		try {
            SystemDatabase.initDatabaseConfig();
            SystemConf.initConfig();
            SystemSchedule.initSchedule();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
