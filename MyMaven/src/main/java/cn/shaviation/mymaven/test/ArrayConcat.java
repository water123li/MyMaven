package cn.shaviation.mymaven.test;

import java.util.StringTokenizer;

import org.junit.Test;

public class ArrayConcat {

	public static void main(String[] args) {
		String string = "h,hhh";
		String[] split = string.split(",");string.length();
		for (String string2 : split) {
			System.out.println(string2);
		}
		
		StringTokenizer st = new StringTokenizer("www.ooobj.com", ".bc");
		while (st.hasMoreElements()) {
			System.out.println("Token:" + st.nextToken());
		}

	}
	
	@Test
	public void test() {
		char[] val = {'a','b','c'};
		String s1  = "黄";
		
		String s2  = "李瑞";
		int comp = s1.compareTo(s2);
		System.out.println(comp );
	}
}