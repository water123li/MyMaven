package cn.shaviation.mymaven.test;

import java.util.StringTokenizer;

public class ArrayConcat {

	public static void main(String[] args) {
		String string = "h,hhh";
		String[] split = string.split(",");
		for (String string2 : split) {
			System.out.println(string2);
		}
		
		StringTokenizer st = new StringTokenizer("www.ooobj.com", ".bc");
		while (st.hasMoreElements()) {
			System.out.println("Token:" + st.nextToken());
		}

	}
}