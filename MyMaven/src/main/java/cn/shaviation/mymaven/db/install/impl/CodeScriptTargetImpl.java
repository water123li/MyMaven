package cn.shaviation.mymaven.db.install.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.shaviation.mymaven.db.install.CodeScriptTarget;
import cn.shaviation.mymaven.db.sql.SqlExecutor;

/**
 * 代码脚本对象接口
 * 
 * @author rli
 *
 */
public class CodeScriptTargetImpl implements CodeScriptTarget {

	private JdbcTemplate jdbcTemplate;
	private	SqlExecutor sqlExecutor;
	
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}
	@Autowired
	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void executeSql(String sqlText) throws Exception{
		this.sqlExecutor.executeSql(sqlText, jdbcTemplate);
	}
	
}
