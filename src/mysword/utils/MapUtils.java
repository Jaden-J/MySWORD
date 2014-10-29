package mysword.utils;

import org.apache.commons.collections.map.ListOrderedMap;

public class MapUtils {
	
	public static String logMapToString(ListOrderedMap lom, String type) {
		if (lom == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer("\n");
		if("shell".equals(type)) {
			for (int i = 0; i < lom.size(); i++) {
				sb.append(lom.get(lom.get(i)));
			}
		} else if("ftp".equals(type)) {
			for (int i = 0; i < lom.size(); i++) {
				sb.append("$" + lom.get(i) + "\n" + lom.get(lom.get(i)));
			}
		}
		return sb.toString();
	}
}
