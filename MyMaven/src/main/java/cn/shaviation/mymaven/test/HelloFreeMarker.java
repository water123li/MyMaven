package cn.shaviation.mymaven.test;

import java.util.*;
import java.io.*;
import freemarker.template.*;

/**
 * Description: <br/>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class HelloFreeMarker {
	// 负责管理FreeMarker模板文件的Configuration实例
	private Configuration cfg;

	// 负责初始化Configuration实例的方法
	public void init() throws Exception {
		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration();
		// 设置FreeMarker的模板文件位置
		cfg.setDirectoryForTemplateLoading(new File("templates"));
	}

	// 处理合并的方法
	public void process() throws Exception {
		// 创建数据模型
		Map root = new HashMap();
		root.put("name", "FreeMarker!");
		root.put("msg", "您已经完成了第一个FreeMarker的示例");
		// 使用Configuration实例来加载指定模板
		Template t = cfg.getTemplate("test.ftl");
		// 处理合并
		t.process(root, new OutputStreamWriter(System.out));
	}

	public static void main(String[] args) throws Exception {
		HelloFreeMarker hf = new HelloFreeMarker();
		hf.init();
		hf.process();
	}
}
