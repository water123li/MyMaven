package cn.shaviation.mymaven.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import cn.shaviation.mymaven.util.JDBCUtil;

public class DatabaseMetaDataTest {

	public static void main(String[] args) {

		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			Connection con = jdbcUtil.getConnection();

			DatabaseMetaData dmd = con.getMetaData();
			System.out.println("数据库名称：" + dmd.getDatabaseProductName());
			System.out.println("数据库版本：" + dmd.getDatabaseProductVersion());
			System.out.println("数据库用户名：" + dmd.getUserName());
			String[] types = { "TABLE" };
			ResultSet rs = dmd.getTables(null, null, "%", types);
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
