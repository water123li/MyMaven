package cn.shaviation.mymaven.db.install;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 代码脚本对象接口
 * 
 * @author rli
 *
 */
public interface CodeScriptTarget {

	/**
	 * 获取JdbcTemplate
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate();
	
	/**
	 * 设置JdbcTemplate
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
