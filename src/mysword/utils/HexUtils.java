package mysword.utils;

import org.apache.commons.lang3.StringUtils;

public class HexUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String stringToHex(String str) {
		StringBuffer strBuf = new StringBuffer();
		if(str != null) {
			for(char c : str.toCharArray()) {
				int i = (int)c;
				strBuf.append("\\u" + StringUtils.leftPad(Integer.toHexString(i), 4, "0"));
			}
		}
		return strBuf.toString();
	}
	
	public String hexToString(String str) {
		StringBuffer strBuf = new StringBuffer();
		if(str != null) {
			for(char c : str.toCharArray()) {
				int i = (int)c;
				strBuf.append("\\u" + StringUtils.rightPad(Integer.toHexString(i), 4, "0"));
			}
		}
		return strBuf.toString();
	}

}
