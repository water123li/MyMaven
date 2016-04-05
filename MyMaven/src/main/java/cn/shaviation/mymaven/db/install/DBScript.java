package cn.shaviation.mymaven.db.install;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据脚本接口
 * 
 * @author rli
 *
 */
public interface DBScript {

	/**
	 * 执行数据脚本
	 * @param jdbcTemplate
	 * @param applyScope	执行脚本的范围
	 * @param currentDbVersion	当前系统版本
	 */
	public void execute(JdbcTemplate jdbcTemplate, String applyScope, String currentDbVersion) throws Exception;

}
