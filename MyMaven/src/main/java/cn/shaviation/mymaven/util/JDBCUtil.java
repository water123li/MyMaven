package cn.shaviation.mymaven.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//数据库连接
public class JDBCUtil {

	private String url;
	private String username;
	private String passwd;

	// 链接数据库的步骤
	// 1 加载数据库的驱动
	public JDBCUtil() {
		url = "jdbc:mysql://127.0.0.1:3306/db_wishingwall";
		username = "root";
		passwd = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 2 获取数据库链接
	public Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}
	// 3 拼接sql语句
	// 4 获取数据库的执行对象Statement
	// 5 获得结果集ResultSet
	// 6 操作结果集
	// 7 ResultSet Statement Connection
	
	public void close(Connection conn,Statement st,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
