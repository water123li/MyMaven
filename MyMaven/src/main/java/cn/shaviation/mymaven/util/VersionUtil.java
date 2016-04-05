package cn.shaviation.mymaven.util;

import java.util.regex.Pattern;

/**
 * 版本工具类
 * @author rli
 *
 */
public class VersionUtil {

	/**
	 * 比较版本号大小
	 * @param oldVersionStr
	 * @param newVersionStr
	 * @return
	 */
	public static int compareVersion(String oldVersionStr, String newVersionStr) {
		String[] oldStrings = oldVersionStr.split(Pattern.quote("."));
		String[] newStrings = newVersionStr.split(Pattern.quote("."));
		int oldLength = oldStrings.length;
		int newLength = newStrings.length;
		if (oldLength != newLength) {
			return oldLength - newLength;
		}
		int n = Math.min(oldLength, newLength);
		for (int i = 0; i < n; i++) {
			int oldInt = Integer.valueOf(oldStrings[i]);
			int newInt = Integer.valueOf(newStrings[i]);
			if (oldInt != newInt) {
				return oldInt - newInt;
			}
		}
		
		return 0;
	}
	
}
