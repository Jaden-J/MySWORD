package mysword.dao.home;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mysword.bean.home.NavigatorBean;
import mysword.bean.home.NavigatorItemBean;
import mysword.system.conf.SystemConf;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class NavigatorDAO {

	private static XStream xs = new XStream(new DomDriver());
	
	static {
		xs.alias("navigator", NavigatorBean.class);
		xs.alias("item", NavigatorItemBean.class);
	}

    private static File getConfigFile() {
        return new File(SystemConf.CONF_HOME + "navigator.xml");
    }
	
	public static NavigatorBean getNavigator() {
		return (NavigatorBean)xs.fromXML(getConfigFile());
	}

	public static void addNavigator(NavigatorItemBean... navigatorList) throws IOException {
		NavigatorBean navigator = (NavigatorBean)xs.fromXML(getConfigFile());
		for(NavigatorItemBean temp : navigatorList) {
			if(StringUtils.isEmpty(temp.getId())) {
				temp.setId(UUID.randomUUID().toString());
			}
			navigator.addItem(temp);
		}
		save(navigator);
	}

	@SuppressWarnings("unused")
	public static void updateNavigator(NavigatorItemBean... navigatorList) throws IOException {
		NavigatorBean navigator = (NavigatorBean)xs.fromXML(getConfigFile());
		for(NavigatorItemBean temp : navigatorList) {
			NavigatorItemBean source = navigator.getItem(temp.getId());
			source = temp;
		}
		save(navigator);
	}
	
	private static void save(NavigatorBean navigator) throws IOException {
        xs.toXML(navigator, new FileOutputStream(getConfigFile()));
//		FileUtils.writeStringToFile(new File(getConfigFile()), xs.toXML(navigator));
	}
}
