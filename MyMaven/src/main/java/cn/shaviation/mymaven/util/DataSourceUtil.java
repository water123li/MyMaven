package cn.shaviation.mymaven.util;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import cn.shaviation.mymaven.bean.DataSourceInfo;

/**
 * 数据源工具类
 * @author Administrator
 *
 */
public class DataSourceUtil {
	/**
	 * 创建数据源
	 * @param host
	 * @param port
	 * @param dbName
	 * @param username
	 * @param password
	 * @return
	 */
	public static BasicDataSource createBasicDataSource(String host, String port, String dbName, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		StringBuilder url = new StringBuilder("jdbc:mysql://").append(host);
		if (port == null || port.trim().isEmpty()) {
			url.append("/");
		}else {
			url.append(":").append(port);
		}
		url.append("/").append(dbName).append("?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=UTF-8");
		dataSource.setUrl(url.toString());
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	/**
	 * 解析数据源
	 * 
	 * @param b
	 * @return
	 */
	public static DataSourceInfo parseDataSource(BasicDataSource dataSource) {
		DataSourceInfo dataSourceInfo = new DataSourceInfo();
		Pattern urlPattern = Pattern.compile("//(.+?)(:(\\d+))?/(.+)\\?");
		Matcher matcher = urlPattern.matcher(dataSource.getUrl());
		if (matcher.find()) {
			dataSourceInfo.setHost(matcher.group(1));
			dataSourceInfo.setPort(matcher.group(3) == null ? "3306" : matcher.group(3));
			dataSourceInfo.setDbName(matcher.group(4));
		}
		dataSourceInfo.setUsername(dataSource.getUsername());
		dataSourceInfo.setPassword(dataSource.getPassword());
		return dataSourceInfo;
	}
	
	/**
	 * 关闭数据源
	 * @param dataSource
	 */
	public static void closeDataSource(DataSource dataSource) {
		if (dataSource instanceof BasicDataSource) {
			try {
				((BasicDataSource)dataSource).close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
