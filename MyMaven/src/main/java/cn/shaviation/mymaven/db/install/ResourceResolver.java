package cn.shaviation.mymaven.db.install;

import java.util.List;

import org.springframework.core.io.Resource;

/**
 * 根据资源路径查找资源文件
 * @author rli
 *
 */
public interface ResourceResolver {

	/**
	 * 根据资源路径查找资源文件
	 * @param resourceLocations
	 * @return
	 */
	public List<Resource> getResources(String resourceLocations);

}
