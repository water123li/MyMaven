package cn.shaviation.mymaven.db.install;
/**
 * 脚本过滤器
 * @author rli
 *
 */
public interface ScriptFilter {

	/**
	 * 判断脚本是否可用
	 * @param applyScope	执行脚本的范围
	 * @param currentDbVersion	当前系统版本
	 * @param scriptScope	脚本适用的范围
	 * @param scriptVersion	脚本应用的系统版本
	 * @param branch	含有脚本的分支版本，多个分支版本以逗号分隔
	 * @return	
	 */
	public boolean isScriptAvailable(String applyScope, String currentDbVersion, String scriptScope,
			String scriptVersion, String branch);

}
