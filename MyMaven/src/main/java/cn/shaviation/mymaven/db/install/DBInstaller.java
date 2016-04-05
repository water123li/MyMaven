package cn.shaviation.mymaven.db.install;

import java.io.File;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库创建及升级功能接口
 * 
 * @author rli
 *
 */
public interface DBInstaller {

	/**
	 * 创建或者升级数据库
	 * 
	 * @param jdbcTemplate
	 */
	public void install(JdbcTemplate jdbcTemplate) throws Exception;

	/**
	 * 判断是否需要升级
	 * 
	 * @param jdbcTemplate
	 * @return
	 * @throws Exception
	 */
	public boolean needUpgrade(JdbcTemplate jdbcTemplate) throws Exception;

	/**
	 * 设置文档的目录，用于文件的备份和还原
	 * 
	 * @param documentFolder
	 */
	public void setDocumentFolder(File documentFolder);

	/**
	 * 执行数据脚本
	 * 
	 * @param applyScope	执行脚本的范围
	 */
	public void setApplyScope(String applyScope);

}
