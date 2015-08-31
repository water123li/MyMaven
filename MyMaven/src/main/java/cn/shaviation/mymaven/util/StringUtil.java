package cn.shaviation.mymaven.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class StringUtil {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		String s = "";
		Long k = 1234567890L;
		System.out.println(getMD5Str(s));
		String s1 = "1";
		System.out.println(getMD5Str(s1));
		BASE64Encoder base64en = new BASE64Encoder();
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		System.out.println(md.getAlgorithm());
		System.out.println(md.hashCode());
		System.out.println(k.hashCode());
	}

	/**
	 * 获取MD5编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {

		MessageDigest md = null;
		String out = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(str.getBytes());
			out = byte2hex(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return out;
	}

	/**
	 * 获取文件MD5编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5File(File file) {

		if (!file.isFile()) {
			return null;
		}
		MessageDigest md = null;
		FileInputStream in = null;
		byte[] buffer = new byte[1024];
		int len;
		try {
			md = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, len);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return byte2hex(md.digest());
	}

	/**
	 * 转化成十六进制
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = java.lang.Integer.toHexString(b[i] & 0XFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}
}
