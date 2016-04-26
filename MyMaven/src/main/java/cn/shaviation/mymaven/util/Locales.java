package cn.shaviation.mymaven.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 获取国际化工具类
 * @author Administrator
 *
 */
public class Locales {

	/**
	 * 获取国际化信息
	 * @param key
	 * @param params 参数
	 * @return
	 */
	public static String getMessage(String key, Object... params) {
		 //设置本地区语言（默认）  
        Locale locale = Locale.getDefault();  
        //可以使用Local的常量设置具体的语言环境  
        //Locale locale = Locale.US;  
        //根据地区不同加载不同的资源文件  
        ResourceBundle rb = ResourceBundle.getBundle("message", locale);  
        //根据key获得value值  
        String title = rb.getString(key); 
        
        String format = MessageFormat.format(title, params);
        
		return format;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getMessage("ddd", "李瑞鹏", "阿甘"));
	}
}
