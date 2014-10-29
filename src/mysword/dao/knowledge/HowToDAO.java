package mysword.dao.knowledge;

import mysword.bean.common.FileBean;
import mysword.system.conf.SystemConf;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class HowToDAO {

	public static ArrayList<FileBean> getHowToFileList() {
		ArrayList<FileBean> result = new ArrayList<FileBean>();
		Collection<File> files = FileUtils.listFiles(new File(getHowToPath()), null, false);
		if( files!=null ) {
			for(File temp : files) {
				FileBean fb = new FileBean();
				fb.setDirectory(temp.isDirectory());
				fb.setFile(temp.isFile());
				fb.setFilename(temp.getName());
				fb.setFileSize(temp.length());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(temp.lastModified());
				fb.setFileTimestamp(cal);
				fb.setFileType(StringUtils.substringAfterLast(temp.getName(), "."));
				fb.setUnknown(StringUtils.isEmpty(StringUtils.substringAfterLast(temp.getName(), "."))?true:false);
				result.add(fb);
			}
		}
		return result;
	}
	
	public static InputStream getHowToFileIO(String filename) throws FileNotFoundException {
		return new FileInputStream(getHowToFile(filename));
	}
	
	public static File getHowToFile(String filename) {
		return FileUtils.getFile(getHowToPath() + filename);
	}
	
	public static String getHowToPath() {
		String howToPath = SystemConf.getConfString("server.howto.path");
		if(StringUtils.length(howToPath) > 0 && StringUtils.endsWithAny(howToPath, "/", "\\")) {
			return howToPath;
		}
		if(StringUtils.length(howToPath) > 0) {
			return howToPath + "/";
		}
		return "";
	}
}
