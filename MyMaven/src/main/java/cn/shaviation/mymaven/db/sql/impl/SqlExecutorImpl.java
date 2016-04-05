package cn.shaviation.mymaven.db.sql.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.shaviation.mymaven.db.sql.SqlExecutor;
import cn.shaviation.mymaven.util.CmdUtil;
import cn.shaviation.mymaven.util.CommonUtil;

@Service("sqlExecutor")
public class SqlExecutorImpl implements SqlExecutor{
	private static final Logger logger = LoggerFactory.getLogger(SqlExecutorImpl.class);
	private Resource mysql; 
	
	public Resource getMysql() {
		return mysql;
	}

	public void setMysql(Resource mysql) {
		this.mysql = mysql;
	}

	@Override
	public File backupDB(JdbcTemplate jdbcTemplate) throws Exception {
		StringBuilder backupFileName = new StringBuilder(CommonUtil.getTempDir()).append("dbBackup")
				.append(System.nanoTime()).append(".sql");
		DbInfo dbInfo = this.parseDbInfo(jdbcTemplate);
		String[] cmd = { "cmd", "/C",
				"\"myslqdump.exe " + " -h" + dbInfo.getHost() + " -p" + dbInfo.getPassword() + " -u"
						+ dbInfo.getUsername() + " --default-character-set=utf8 -p" + dbInfo.getPassword() + " "
						+ dbInfo.getDbName() + "\"" };
		
		File backupFile = new File(backupFileName.toString());
		CmdUtil.excuteCmd(cmd, mysql.getFile(), backupFile, "utf8");
		logger.info(" DataBase " + dbInfo.getDbName() + "has been backuped to directory: " + backupFileName);
		return backupFile;
	}

	@Override
	public void executeSql(File sqlFile, JdbcTemplate jdbcTemplate, boolean recreateDB) throws Exception {
		DbInfo dbInfo = this.parseDbInfo(jdbcTemplate);
		String[] elements = { "\"mysl", " -h", dbInfo.getHost(), " -p", dbInfo.getPort(), " -u", dbInfo.getUsername(),
				" -p", dbInfo.getPassword(), " " };
		String mysqlBin = StringUtils.join(elements, "");
		if (recreateDB) {
			StringBuilder recreateDbSqlFileName = new StringBuilder(CommonUtil.getTempDir()).append("recreateDb")
					.append(System.nanoTime()).append(".sql");
			File recreateDbSqlFile = new File(recreateDbSqlFileName.toString());
			String sqlString = "DROP DATABASE IF EXIST " + dbInfo.getDbName() + ";create database if not exists "
					+ dbInfo.getDbName() + " default character set utf8 collate utf8_bin";
			FileUtils.writeStringToFile(recreateDbSqlFile, sqlString);

			String[] dropDbCmd = { "cmd", "/C",
					StringUtils.join(new String[] { mysqlBin, "<\"", recreateDbSqlFile.getAbsolutePath(), "\"\"" }),
					"" };
			CmdUtil.excuteCmd(dropDbCmd, mysql.getFile(), null);
			
			recreateDbSqlFile.delete();
		}
		String[] sqlCmd = {"cmd", "/C",StringUtils.join(new String[]{mysqlBin,dbInfo.getDbName(),"<\"",sqlFile.getAbsolutePath(),"\"\""},"")};
		CmdUtil.excuteCmd(sqlCmd, mysql.getFile(), null);
	}

	@Override
	public void executeSql(String sqlText, JdbcTemplate jdbcTemplate) {
		String[] sqlArray = sqlText.split("(;\\s*\\r\\n)|(;\\s*\\n)");
		jdbcTemplate.execute("set foreign_key_checks=0");
		for (String sql : sqlArray) {
			jdbcTemplate.execute(sql);
		}
	}
	
	private  DbInfo parseDbInfo(JdbcTemplate jdbcTemplate) {
		DbInfo dbInfo = new DbInfo();
		BasicDataSource dataSource = (BasicDataSource) jdbcTemplate.getDataSource();
		dbInfo.setUsername(dataSource.getUsername());
		dbInfo.setPassword(dataSource.getPassword());
		
		Pattern pattern = Pattern.compile("//(.*?):(\\d+)/(.*?)\\?");
		Matcher matcher = pattern.matcher(dataSource.getUrl());
		while (matcher.find()) {
			dbInfo.setHost(matcher.group(1));
			dbInfo.setPort(matcher.group(2));
			dbInfo.setDbName(matcher.group(3));
		}
		return dbInfo;
	}
	
	class DbInfo{
		private String host;
		private String port;
		private String username;
		private String password;
		private String dbName;
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDbName() {
			return dbName;
		}
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		
	}
	
}
