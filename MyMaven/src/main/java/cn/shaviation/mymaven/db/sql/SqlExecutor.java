package cn.shaviation.mymaven.db.sql;

import java.io.File;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SqlExecutor {
	/**
	 * 备份数据库
	 * @param jdbcTemplate
	 * @return 数据库备份文件
	 * @throws Exception
	 */
	public File backupDB(JdbcTemplate jdbcTemplate) throws Exception;
	/**
	 * 执行sql脚本文件
	 * @param file		sql脚本文件
	 * @param jdbcTemplate
	 * @param recreateDB 是否重建数据库
	 */
	public void executeSql(File sqlFile, JdbcTemplate jdbcTemplate, boolean recreateDB) throws Exception;
	
	/**
	 * 执行sql语句，多条语句以;分隔
	 * @param sqlText
	 * @param jdbcTemplate
	 * @param recreateDB
	 */
	public void executeSql(String sqlText, JdbcTemplate jdbcTemplate) throws Exception;
}
